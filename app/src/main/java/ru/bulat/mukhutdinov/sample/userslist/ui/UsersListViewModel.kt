package ru.bulat.mukhutdinov.sample.userslist.ui

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import ru.bulat.mukhutdinov.sample.infrastructure.common.ui.BaseViewModel
import ru.bulat.mukhutdinov.sample.infrastructure.exception.SampleException
import ru.bulat.mukhutdinov.sample.infrastructure.util.data.Either
import ru.bulat.mukhutdinov.sample.user.model.User

interface UsersListViewModel : BaseViewModel {

    val users: LiveData<Either<List<User>, SampleException>>

    var lifecycleOwner: LifecycleOwner

    val onUserClicked: LiveData<Either<User, SampleException>>

    fun onUserClicked(user: User)
}