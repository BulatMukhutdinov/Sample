package ru.bulat.mukhutdinov.sample.postslist.ui

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import ru.bulat.mukhutdinov.sample.infrastructure.common.model.NetworkState
import ru.bulat.mukhutdinov.sample.infrastructure.common.ui.BaseViewModel
import ru.bulat.mukhutdinov.sample.post.model.Post

interface PostsListViewModel : BaseViewModel {

    val posts: LiveData<PagedList<Post>>

    val networkState: LiveData<NetworkState>

    val refreshState: LiveData<NetworkState>

    val errorText: LiveData<String>

    fun refresh()

    fun retry()

    fun updateNetworkState(state: NetworkState)
}