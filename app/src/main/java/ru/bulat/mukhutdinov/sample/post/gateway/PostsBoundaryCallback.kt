package ru.bulat.mukhutdinov.sample.post.gateway

import androidx.annotation.MainThread
import androidx.paging.PagedList
import androidx.paging.PagingRequestHelper
import com.tumblr.jumblr.JumblrClient
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.bulat.mukhutdinov.sample.infrastructure.common.gateway.Config
import ru.bulat.mukhutdinov.sample.infrastructure.extension.createStatusLiveData
import ru.bulat.mukhutdinov.sample.post.model.Post
import ru.bulat.mukhutdinov.sample.post.model.PostDto
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class PostsBoundaryCallback(
    private val jumblr: JumblrClient,
    private val compositeDisposable: CompositeDisposable,
    private val handleResponse: (List<PostDto>) -> Unit,
    private val networkPageSize: Int
) : PagedList.BoundaryCallback<Post>() {

    val helper = PagingRequestHelper(Executors.newSingleThreadExecutor())
    val networkState = helper.createStatusLiveData()

    /**
     * Database returned 0 items. We should query the backend for more items.
     */
    @MainThread
    override fun onZeroItemsLoaded() {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) { pagingHelperCallback ->
            compositeDisposable.add(
                Single
                    .fromCallable {
                        val options = HashMap<String, Any>()
                        options["limit"] = networkPageSize
                        options["type"] = "text"

                        jumblr.userDashboard(options)
                    }
                    .timeout(Config.TIMEOUT_SEC, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        { posts ->
                            handleResponse(posts)
                            pagingHelperCallback.recordSuccess()
                        },
                        { pagingHelperCallback.recordFailure(it) })
            )
        }
    }

    /**
     * User reached to the end of the list.
     */
    @MainThread
    override fun onItemAtEndLoaded(itemAtEnd: Post) {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) { pagingHelperCallback ->
            compositeDisposable.add(
                Single
                    .fromCallable {
                        val options = HashMap<String, Any>()
                        options["limit"] = networkPageSize
                        options["since_id"] = itemAtEnd.id
                        options["type"] = "text"

                        jumblr.userDashboard(options)
                    }
                    .timeout(Config.TIMEOUT_SEC, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        { posts ->
                            handleResponse(posts)
                            pagingHelperCallback.recordSuccess()
                        },
                        { pagingHelperCallback.recordFailure(it) })
            )
        }
    }
}