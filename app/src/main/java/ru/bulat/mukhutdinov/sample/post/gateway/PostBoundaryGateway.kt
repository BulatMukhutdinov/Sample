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
import ru.bulat.mukhutdinov.sample.infrastructure.common.model.Listing
import ru.bulat.mukhutdinov.sample.infrastructure.common.model.NetworkState
import ru.bulat.mukhutdinov.sample.infrastructure.exception.mapLocalExceptions
import ru.bulat.mukhutdinov.sample.post.db.PostDao
import ru.bulat.mukhutdinov.sample.post.model.Post
import ru.bulat.mukhutdinov.sample.post.model.PostConverter
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
    override fun getPaged(pageSize: Int): Listing<Post> {
        val boundaryCallback = PostsBoundaryCallback(
            jumblr = jumblr,
            compositeDisposable = compositeDisposable,
            handleResponse = { posts -> postDao.insertAll(PostConverter.toDatabase(posts)) },
            networkPageSize = networkPageSize)

        val refreshTrigger = MutableLiveData<Unit>()
        val refreshState = Transformations.switchMap(refreshTrigger) {
            refresh()
        }

        val livePagedList = postDao.getAll()
            .map { PostConverter.fromDatabase(it) }
            .toLiveData(
                pageSize = pageSize,
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
                    val options = HashMap<String, Int>()
                    options["limit"] = networkPageSize

                    jumblr.userDashboard(options)
                }
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        db.runInTransaction {
                            postDao.clear()
                            postDao.insertAll(PostConverter.toDatabase(it))
                        }
                        networkState.postValue(NetworkState.Loaded)
                    },
                    { networkState.postValue(NetworkState.Error(it.message)) })
        )

        return networkState
    }

    override fun findById(id: String): Maybe<Post> =
        postDao.findById(id)
            .map { PostConverter.fromDatabase(it) }
            .mapLocalExceptions()

    override fun update(post: Post): Completable =
        Completable.fromCallable { postDao.update(PostConverter.toDatabase(post)) }
            // emulate processing delay
            .delay(2, TimeUnit.SECONDS)
            .mapLocalExceptions()

    override fun clearSubscriptions() {
        compositeDisposable.clear()
    }
}