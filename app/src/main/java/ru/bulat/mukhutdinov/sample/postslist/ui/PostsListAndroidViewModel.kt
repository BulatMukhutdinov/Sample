package ru.bulat.mukhutdinov.sample.postslist.ui

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import ru.bulat.mukhutdinov.sample.infrastructure.common.model.Listing
import ru.bulat.mukhutdinov.sample.infrastructure.common.model.NetworkState
import ru.bulat.mukhutdinov.sample.infrastructure.common.ui.BaseAndroidViewModel
import ru.bulat.mukhutdinov.sample.infrastructure.exception.SampleException
import ru.bulat.mukhutdinov.sample.infrastructure.util.Either
import ru.bulat.mukhutdinov.sample.post.gateway.PostGateway
import ru.bulat.mukhutdinov.sample.post.model.Post

class PostsListAndroidViewModel(postsGateway: PostGateway)
    : BaseAndroidViewModel(), PostsListViewModel {

    private val postsListing = MutableLiveData<Listing<Post>>()
        .also { it.value = postsGateway.getPaged(30) }

    override val posts: LiveData<Either<PagedList<Post>, SampleException>> =
        Transformations.switchMap(postsListing) { it.pagedList }

    override val networkState: LiveData<NetworkState> = Transformations.switchMap(postsListing) { it.networkState }

    override val refreshState: LiveData<NetworkState> = Transformations.switchMap(postsListing) { it.refreshState }

    override lateinit var lifecycleOwner: LifecycleOwner

    override fun refresh() {
        postsListing.value?.refresh?.invoke()
    }

    override fun retry() {
        val listing = postsListing.value
        listing?.retry?.invoke()
    }
}