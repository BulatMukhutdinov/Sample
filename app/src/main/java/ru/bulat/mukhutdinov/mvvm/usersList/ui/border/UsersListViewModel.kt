package ru.bulat.mukhutdinov.mvvm.usersList.ui.border

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import ru.bulat.mukhutdinov.mvvm.common.ui.BaseViewModel
import ru.bulat.mukhutdinov.mvvm.user.model.User

interface UsersListViewModel : BaseViewModel<UsersListView> {

    val users: LiveData<List<User>>

    val lifecycleOwner: LifecycleOwner

    fun onUserClicked(user: User)
}