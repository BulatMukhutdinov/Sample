package ru.bulat.mukhutdinov.sample.postslist.ui

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import ru.bulat.mukhutdinov.sample.infrastructure.common.model.NetworkState
import ru.bulat.mukhutdinov.sample.infrastructure.common.ui.BaseViewModel
import ru.bulat.mukhutdinov.sample.infrastructure.exception.SampleException
import ru.bulat.mukhutdinov.sample.infrastructure.util.Either
import ru.bulat.mukhutdinov.sample.post.model.Post

interface PostsListViewModel : BaseViewModel {

    var lifecycleOwner: LifecycleOwner

    val posts: LiveData<Either<PagedList<Post>, SampleException>>

    val networkState: LiveData<NetworkState>

    val refreshState: LiveData<NetworkState>

    fun refresh()

    fun retry()
}