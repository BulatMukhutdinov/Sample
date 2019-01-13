package ru.bulat.mukhutdinov.sample.main.ui

import androidx.lifecycle.LiveData
import ru.bulat.mukhutdinov.sample.infrastructure.common.ui.BaseViewModel
import ru.bulat.mukhutdinov.sample.infrastructure.exception.SampleException
import ru.bulat.mukhutdinov.sample.infrastructure.util.data.Either

interface MainViewModel : BaseViewModel {

    val onUsersListAppClicked: LiveData<Either<Unit, SampleException>>

    fun onUsersListAppClicked()

    val onPostsListAppClicked: LiveData<Either<Unit, SampleException>>

    fun onPostsListAppClicked()
}