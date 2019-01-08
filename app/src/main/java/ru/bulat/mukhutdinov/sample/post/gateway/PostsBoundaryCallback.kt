package ru.bulat.mukhutdinov.sample.post.gateway

import androidx.annotation.MainThread
import androidx.paging.PagedList
import androidx.paging.PagingRequestHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.bulat.mukhutdinov.sample.infrastructure.common.api.SampleApi
import ru.bulat.mukhutdinov.sample.infrastructure.util.createStatusLiveData
import ru.bulat.mukhutdinov.sample.post.api.PostDto
import ru.bulat.mukhutdinov.sample.post.model.Post
import java.util.concurrent.Executor

class PostsBoundaryCallback(
    private val api: SampleApi,
    private val handleResponse: (List<PostDto>) -> Unit,
    private val executor: Executor,
    private val networkPageSize: Int
) : PagedList.BoundaryCallback<Post>() {

    val helper = PagingRequestHelper(executor)
    val networkState = helper.createStatusLiveData()

    /**
     * Database returned 0 items. We should query the backend for more items.
     */
    @MainThread
    override fun onZeroItemsLoaded() {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) {
            api.get(networkPageSize)
                .enqueue(createApiCallback(it))
        }
    }

    /**
     * User reached to the end of the list.
     */
    @MainThread
    override fun onItemAtEndLoaded(itemAtEnd: Post) {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) {
            api.getSince(pageSize = networkPageSize, since = itemAtEnd.id)
                .enqueue(createApiCallback(it))
        }
    }

    /**
     * every time it gets new items, boundary callback simply inserts them into the database and
     * paging library takes care of refreshing the list if necessary.
     */
    private fun insertItemsIntoDb(response: Response<List<PostDto>>, it: PagingRequestHelper.Request.Callback) {
        val posts = response.body() ?: emptyList()

        executor.execute {
            handleResponse(posts)
            it.recordSuccess()
        }
    }

    private fun createApiCallback(it: PagingRequestHelper.Request.Callback)
        : Callback<List<PostDto>> {
        return object : Callback<List<PostDto>> {

            override fun onFailure(call: Call<List<PostDto>>, t: Throwable) {
                it.recordFailure(t)
            }

            override fun onResponse(call: Call<List<PostDto>>, response: Response<List<PostDto>>) {
                insertItemsIntoDb(response, it)
            }
        }
    }
}