package ru.bulat.mukhutdinov.sample.postslist.ui

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import ru.bulat.mukhutdinov.sample.infrastructure.common.model.Listing
import ru.bulat.mukhutdinov.sample.infrastructure.common.model.NetworkState
import ru.bulat.mukhutdinov.sample.infrastructure.common.ui.BaseAndroidViewModel
import ru.bulat.mukhutdinov.sample.post.gateway.PostGateway
import ru.bulat.mukhutdinov.sample.post.model.Post

class PostsListAndroidViewModel(postsGateway: PostGateway)
    : BaseAndroidViewModel(), PostsListViewModel {

    override val errorText = ObservableField<String>("")

    override val isRefreshing = ObservableBoolean(false)

    override val isRetryEnabled = ObservableBoolean(true)

    private val postsListing = MutableLiveData<Listing<Post>>()
        .also { it.value = postsGateway.getPaged(10) }

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
}