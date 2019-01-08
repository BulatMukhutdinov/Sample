package ru.bulat.mukhutdinov.sample.postslist.ui

import androidx.databinding.BindingAdapter
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.bulat.mukhutdinov.sample.infrastructure.common.model.NetworkState
import ru.bulat.mukhutdinov.sample.infrastructure.common.model.Status
import ru.bulat.mukhutdinov.sample.postslist.ui.adapter.PostsAdapter

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
            changeLoadingVisibility(it)

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

// todo implement hiding or showing loading progress bar (in toolbar)
private fun changeLoadingVisibility(networkState: NetworkState) {
    if (networkState == NetworkState.Loading) {

    } else {

    }
}