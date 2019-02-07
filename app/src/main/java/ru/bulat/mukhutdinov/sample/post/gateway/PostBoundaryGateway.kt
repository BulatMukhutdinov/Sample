package ru.bulat.mukhutdinov.sample.post.gateway

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.toLiveData
import com.tumblr.jumblr.JumblrClient
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.bulat.mukhutdinov.sample.infrastructure.common.db.SampleDatabase
import ru.bulat.mukhutdinov.sample.infrastructure.common.gateway.Config.TIMEOUT_SEC
import ru.bulat.mukhutdinov.sample.infrastructure.common.model.Listing
import ru.bulat.mukhutdinov.sample.infrastructure.common.model.NetworkState
import ru.bulat.mukhutdinov.sample.infrastructure.exception.mapLocalExceptions
import ru.bulat.mukhutdinov.sample.post.db.PostDao
import ru.bulat.mukhutdinov.sample.post.model.Post
import ru.bulat.mukhutdinov.sample.post.model.PostConverter
import ru.bulat.mukhutdinov.sample.post.model.PostDto
import ru.bulat.mukhutdinov.sample.post.model.PostText
import java.util.concurrent.TimeUnit

class PostBoundaryGateway(
    private val db: SampleDatabase,
    private val postDao: PostDao,
    private val jumblr: JumblrClient,
    private val blogName: String, // should be used later to retrieve posts of given blog
    private val networkPageSize: Int
) : PostGateway {

    private val compositeDisposable = CompositeDisposable()

    @MainThread
    override fun getPaged(): Listing<Post> {
        val boundaryCallback = PostsBoundaryCallback(
            jumblr = jumblr,
            compositeDisposable = compositeDisposable,
            handleResponse = { posts -> insertIntoDatabase(posts) },
            networkPageSize = networkPageSize)

        val refreshTrigger = MutableLiveData<Unit>()
        val refreshState = Transformations.switchMap(refreshTrigger) {
            refresh()
        }

        val livePagedList = postDao.getAll()
            .map { PostConverter.fromDatabase(it) }
            .toLiveData(
                pageSize = networkPageSize,
                boundaryCallback = boundaryCallback
            )

        return Listing(
            pagedList = livePagedList,
            networkState = boundaryCallback.networkState,
            retry = { boundaryCallback.helper.retryAllFailed() },
            refresh = { refreshTrigger.value = null },
            refreshState = refreshState
        )
    }

    /**
     * When refresh is called, we simply run a fresh network request and when it arrives, clear
     * the database table and insert all new items in a transaction.
     * <p>
     * Since the PagedList already uses a database bound data source, it will automatically be
     * updated after the database transaction is finished.
     */
    @MainThread
    private fun refresh(): LiveData<NetworkState> {
        val networkState = MutableLiveData<NetworkState>()
        networkState.value = NetworkState.Loading

        compositeDisposable.add(
            Single
                .fromCallable {
                    val options = HashMap<String, Any>()
                    options["limit"] = networkPageSize
                    options["type"] = "text"

                    jumblr.userDashboard(options)
                }
                .subscribeOn(Schedulers.io())
                .timeout(TIMEOUT_SEC, TimeUnit.SECONDS)
                .subscribe(
                    { posts ->
                        posts[0].type
                        db.runInTransaction {
                            postDao.clear()
                            insertIntoDatabase(posts)
                        }
                        networkState.postValue(NetworkState.Loaded)
                    },
                    { networkState.postValue(NetworkState.Error(it.message)) })
        )

        return networkState
    }

    private fun insertIntoDatabase(posts: List<PostDto>) {
        posts.forEach { postDto ->
            val postEntity = PostConverter.fromNetwork(postDto)
            postEntity.avatar = jumblr.blogInfo("${postDto.blogName}.tumblr.com")?.avatar()
            postDao.insert(postEntity)
        }
    }

    override fun findById(id: String): Maybe<Post> =
        postDao.findById(id)
            .map { PostConverter.fromDatabase(it) }
            .mapLocalExceptions()

    override fun update(post: Post): Completable =
        Completable
            .fromCallable {
                // todo remove if clause and implement each type to entity converting
                if (post is PostText) {
                    postDao.update(PostConverter.toDatabase(post))
                }
            }
            // emulate processing delay
            .delay(2, TimeUnit.SECONDS)
            .mapLocalExceptions()

    override fun clearSubscriptions() {
        compositeDisposable.clear()
    }
}