package ru.bulat.mukhutdinov.mvvm.user.ui

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import ru.bulat.mukhutdinov.mvvm.infrastructure.common.ui.BaseViewModel
import ru.bulat.mukhutdinov.mvvm.user.model.User

interface UserViewModel : BaseViewModel {

    val user: ObservableField<User>

    val onSaveClicked : LiveData<Unit>

    fun onSaveClicked()
}