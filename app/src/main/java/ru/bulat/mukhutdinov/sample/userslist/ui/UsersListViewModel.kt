package ru.bulat.mukhutdinov.sample.userslist.ui

import androidx.lifecycle.LifecycleOwner
import ru.bulat.mukhutdinov.sample.infrastructure.common.ui.BaseViewModel
import ru.bulat.mukhutdinov.sample.infrastructure.util.data.DataStateLiveData
import ru.bulat.mukhutdinov.sample.user.model.User

interface UsersListViewModel : BaseViewModel {

    val users: DataStateLiveData<List<User>>

    var lifecycleOwner: LifecycleOwner

    val onUserClicked: DataStateLiveData<User>

    fun onUserClicked(user: User)
}