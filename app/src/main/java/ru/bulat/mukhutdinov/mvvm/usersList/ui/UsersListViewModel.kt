package ru.bulat.mukhutdinov.mvvm.usersList.ui

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import ru.bulat.mukhutdinov.mvvm.infrastructure.common.ui.BaseViewModel
import ru.bulat.mukhutdinov.mvvm.infrastructure.exception.MvvmException
import ru.bulat.mukhutdinov.mvvm.infrastructure.util.Either
import ru.bulat.mukhutdinov.mvvm.infrastructure.util.SingleLiveEvent
import ru.bulat.mukhutdinov.mvvm.user.model.User

interface UsersListViewModel : BaseViewModel {

    val users: LiveData<Either<List<User>, MvvmException>>

    var lifecycleOwner: LifecycleOwner

    val onUserClicked: SingleLiveEvent<User>

    fun onUserClicked(user: User)
}