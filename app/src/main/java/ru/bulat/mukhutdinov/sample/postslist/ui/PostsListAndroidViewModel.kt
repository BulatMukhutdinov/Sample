package ru.bulat.mukhutdinov.sample.postslist.ui

import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.bulat.mukhutdinov.sample.infrastructure.common.model.Listing
import ru.bulat.mukhutdinov.sample.infrastructure.common.model.NetworkState
import ru.bulat.mukhutdinov.sample.infrastructure.common.model.Status
import ru.bulat.mukhutdinov.sample.infrastructure.common.ui.BaseAndroidViewModel
import ru.bulat.mukhutdinov.sample.post.gateway.PostGateway
import ru.bulat.mukhutdinov.sample.post.model.Post
import ru.bulat.mukhutdinov.sample.postslist.ui.adapter.PostsAdapter

class PostsListAndroidViewModel(private val postGateway: PostGateway)
    : BaseAndroidViewModel(), PostsListViewModel {

    override val errorText = ObservableField<String>("")

    override val isRefreshing = ObservableBoolean(false)

    override val isRetryEnabled = ObservableBoolean(true)

    private val postsListing = MutableLiveData<Listing<Post>>()
        .also { it.value = postGateway.getPaged(10) }

    override val posts: LiveData<PagedList<Post>> = Transformations.switchMap(postsListing) { it.pagedList }

    override val networkState: LiveData<NetworkState> = Transformations.switchMap(postsListing) { it.networkState }

    override val refreshState: LiveData<NetworkState> = Transformations.switchMap(postsListing) { it.refreshState }

    override lateinit var lifecycleOwner: LifecycleOwner

    override fun refresh() {
        postsListing.value?.refresh?.invoke()
    }

    override fun retry() {
        postsListing.value?.retry?.invoke()
    }

    override fun onCleared() {
        super.onCleared()
        postGateway.clearSubscriptions()
    }
}

@BindingAdapter("app:postsListViewModel")
fun bind(recyclerView: RecyclerView, postsListViewModel: PostsListViewModel) {
    val adapter = PostsAdapter(postsListViewModel)

    recyclerView.adapter = adapter
    recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)

    postsListViewModel.posts.observe(postsListViewModel.lifecycleOwner, Observer {
        adapter.submitList(it)
    })

    postsListViewModel.networkState.observe(postsListViewModel.lifecycleOwner, Observer {
        if (it.status == Status.FAILED) {
            postsListViewModel.isRetryEnabled.set(true)
            postsListViewModel.errorText.set((it as NetworkState.Error).message)
        } else {
            postsListViewModel.isRetryEnabled.set(false)
            postsListViewModel.errorText.set("")
        }
        adapter.setNetworkState(it)
    })

    postsListViewModel.refreshState.observe(postsListViewModel.lifecycleOwner, Observer {
        if (it == NetworkState.Loading) {
            postsListViewModel.isRefreshing.set(true)
        } else {
            postsListViewModel.isRefreshing.set(false)
        }
    })
}