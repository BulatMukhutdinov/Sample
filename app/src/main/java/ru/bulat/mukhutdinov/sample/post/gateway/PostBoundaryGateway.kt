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
import ru.bulat.mukhutdinov.sample.post.model.*
import java.util.concurrent.TimeUnit

class PostBoundaryGateway(
        private val db: SampleDatabase,
        private val postDao: PostDao,
        private val jumblr: JumblrClient,
        private val blogName: String, // should be used later to retrieve posts of given blog
        private val networkPageSize: Int
) : PostGateway {

    override val currentBlogName: String = blogName

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
                            options["filter"] = "text"

                            jumblr.userDashboard(options)
                        }
                        .subscribeOn(Schedulers.io())
                        .timeout(TIMEOUT_SEC, TimeUnit.SECONDS)
                        .subscribe(
                                { posts ->
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
            postEntity.avatar = jumblr.blogAvatar(postDto.blogName)
            postDao.insert(postEntity)
        }
    }

    override fun findById(id: Long): Maybe<Post> =
            postDao.findById(id)
                    .map { PostConverter.fromDatabase(it) }
                    .mapLocalExceptions()

    override fun updateId(oldId: Long, newId: Long): Completable =
            Completable
                    .fromCallable {
                        postDao.updateId(oldId, newId)
                    }
                    .mapLocalExceptions()

    override fun delete(post: TextPost): Completable =
            Completable
                    .fromCallable {
                        postDao.delete(PostConverter.toDatabase(post))
                    }
                    .mapLocalExceptions()

    override fun createTextPostLocal(post: TextPost): Completable =
            Completable
                    .fromCallable {
                        postDao.insert(PostConverter.toDatabase(post))
                    }
                    .mapLocalExceptions()

    override fun createTextPostRemote(post: TextPost): Completable =
            Completable
                    .fromCallable {
                        val newPost = jumblr.newPost(blogName, TextPostDto::class.java)
                        newPost.title = post.title
                        newPost.body = post.body
                        newPost.save()

                        postDao.updateId(post.id, newPost.id)
                    }

    override fun currentAvatar(): Single<String> =
            Single.fromCallable {
                jumblr.blogAvatar(blogName)
            }

    override fun clearSubscriptions() {
        compositeDisposable.clear()
    }
}