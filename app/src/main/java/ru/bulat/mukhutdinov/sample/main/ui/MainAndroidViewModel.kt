package ru.bulat.mukhutdinov.sample.main.ui

import ru.bulat.mukhutdinov.sample.infrastructure.common.ui.BaseAndroidViewModel
import ru.bulat.mukhutdinov.sample.infrastructure.exception.SampleException
import ru.bulat.mukhutdinov.sample.infrastructure.util.Either
import ru.bulat.mukhutdinov.sample.infrastructure.util.SingleLiveEvent

class MainAndroidViewModel : BaseAndroidViewModel(), MainViewModel {

    override val onUsersListAppClicked = SingleLiveEvent<Either<Unit, SampleException>>()

    override val onPostsListAppClicked = SingleLiveEvent<Either<Unit, SampleException>>()

    override fun onUsersListAppClicked() {
        onUsersListAppClicked.postValue(Either.Complete)
    }

    override fun onPostsListAppClicked() {
        onPostsListAppClicked.postValue(Either.Complete)
    }
}