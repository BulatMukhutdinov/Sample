package ru.bulat.mukhutdinov.sample.postslist.ui

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import ru.bulat.mukhutdinov.sample.infrastructure.common.model.NetworkState
import ru.bulat.mukhutdinov.sample.infrastructure.common.ui.BaseViewModel
import ru.bulat.mukhutdinov.sample.post.model.Post

interface PostsListViewModel : BaseViewModel {

    var lifecycleOwner: LifecycleOwner

    val posts: LiveData<PagedList<Post>>

    val networkState: LiveData<NetworkState>

    val refreshState: LiveData<NetworkState>

    val isRetryEnabled: ObservableBoolean

    val isRefreshing: ObservableBoolean

    val errorText: ObservableField<String>

    fun refresh()

    fun retry()

    fun onMenuClick()

    fun onAddClick()
}