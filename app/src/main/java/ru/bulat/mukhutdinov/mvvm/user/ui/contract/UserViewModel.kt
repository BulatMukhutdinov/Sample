package ru.bulat.mukhutdinov.mvvm.user.ui.contract

import androidx.databinding.ObservableField
import ru.bulat.mukhutdinov.mvvm.common.ui.BaseViewModel
import ru.bulat.mukhutdinov.mvvm.user.model.User

interface UserViewModel : BaseViewModel<UserView> {

    var user: ObservableField<User>

    fun onSaveClicked()
}