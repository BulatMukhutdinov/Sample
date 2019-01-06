package ru.bulat.mukhutdinov.mvvm.user.ui

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import ru.bulat.mukhutdinov.mvvm.infrastructure.common.ui.BaseViewModel
import ru.bulat.mukhutdinov.mvvm.infrastructure.exception.MvvmException
import ru.bulat.mukhutdinov.mvvm.infrastructure.util.Either
import ru.bulat.mukhutdinov.mvvm.user.model.User

interface UserViewModel : BaseViewModel {

    val user: ObservableField<User>

    val isProgressVisible: ObservableBoolean

    val isSaveEnabled: ObservableBoolean

    val onSaveClicked: LiveData<Either<Nothing, MvvmException>>

    fun onSaveClicked()
}