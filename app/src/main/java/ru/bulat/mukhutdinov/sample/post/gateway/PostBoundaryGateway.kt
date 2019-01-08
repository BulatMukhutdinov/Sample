package ru.bulat.mukhutdinov.sample.post.gateway

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import androidx.paging.toLiveData
import io.reactivex.Completable
import io.reactivex.Maybe
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.bulat.mukhutdinov.sample.infrastructure.common.api.SampleApi
import ru.bulat.mukhutdinov.sample.infrastructure.common.db.SampleDatabase
import ru.bulat.mukhutdinov.sample.infrastructure.common.model.Listing
import ru.bulat.mukhutdinov.sample.infrastructure.common.model.NetworkState
import ru.bulat.mukhutdinov.sample.infrastructure.exception.SampleException
import ru.bulat.mukhutdinov.sample.infrastructure.exception.mapLocalExceptions
import ru.bulat.mukhutdinov.sample.infrastructure.util.Either
import ru.bulat.mukhutdinov.sample.post.api.PostDto
import ru.bulat.mukhutdinov.sample.post.db.PostDao
import ru.bulat.mukhutdinov.sample.post.model.Post
import ru.bulat.mukhutdinov.sample.post.model.PostConverter
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class PostBoundaryGateway(
    private val db: SampleDatabase,
    private val postDao: PostDao,
    private val api: SampleApi,
    private val executor: Executor,
    private val networkPageSize: Int
) : PostGateway {

    @MainThread
    override fun getPaged(pageSize: Int): Listing<Post> {
        val boundaryCallback = PostsBoundaryCallback(
            api = api,
            handleResponse = { posts -> postDao.insertAll(PostConverter.toDatabase(posts)) },
            executor = executor,
            networkPageSize = networkPageSize)

        val refreshTrigger = MutableLiveData<Unit>()
        val refreshState = Transformations.switchMap(refreshTrigger) { refresh() }

        val livePagedList = postDao.getAll()
            .map { PostConverter.fromDatabase(it) }
            .toLiveData(
                pageSize = pageSize,
                boundaryCallback = boundaryCallback
            )

        val eitherPagedList = Transformations.map(livePagedList) { pagedList ->
            val either: Either<PagedList<Post>, SampleException> = Either.Data(pagedList)
            either
        }

        return Listing(
            pagedList = eitherPagedList,
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

        api.get(networkPageSize).enqueue(
            object : Callback<List<PostDto>> {
                override fun onFailure(call: Call<List<PostDto>>, throwable: Throwable) {
                    // retrofit calls this on main thread so safe to call set value
                    networkState.value = NetworkState.Error(throwable.message)
                }

                override fun onResponse(call: Call<List<PostDto>>, response: Response<List<PostDto>>) {
                    executor.execute {
                        db.runInTransaction {
                            postDao.clear()
                            postDao.insertAll(PostConverter.toDatabase(response.body() ?: emptyList()))
                        }
                        // since we are in bg thread now, post the result.
                        networkState.postValue(NetworkState.Loaded)
                    }
                }
            }
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
}