package ru.bulat.mukhutdinov.sample.postslist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import ru.bulat.mukhutdinov.sample.infrastructure.common.model.Listing
import ru.bulat.mukhutdinov.sample.infrastructure.common.model.NetworkState
import ru.bulat.mukhutdinov.sample.infrastructure.common.model.Status
import ru.bulat.mukhutdinov.sample.infrastructure.common.ui.BaseAndroidViewModel
import ru.bulat.mukhutdinov.sample.post.gateway.PostGateway
import ru.bulat.mukhutdinov.sample.post.model.Post

class PostsListAndroidViewModel(private val postGateway: PostGateway)
    : BaseAndroidViewModel(), PostsListViewModel {

    private val postsListing = MutableLiveData<Listing<Post>>()
        .also { it.value = postGateway.getPaged(20) }

    override val posts: LiveData<PagedList<Post>> = Transformations.switchMap(postsListing) { it.pagedList }

    override val networkState: LiveData<NetworkState> = Transformations.switchMap(postsListing) { it.networkState }

    override val refreshState: LiveData<NetworkState> = Transformations.switchMap(postsListing) { it.refreshState }

    override val errorText: MutableLiveData<String> = MutableLiveData()

    override fun refresh() {
        postsListing.value?.refresh?.invoke()
    }

    override fun retry() {
        postsListing.value?.retry?.invoke()
    }

    override fun updateNetworkState(state: NetworkState) {
        val text = if (state.status == Status.FAILED) {
            ((state as NetworkState.Error).message).orEmpty()
        } else {
            ""
        }

        errorText.postValue(text)
    }

    override fun onCleared() {
        super.onCleared()
        postGateway.clearSubscriptions()
    }
}