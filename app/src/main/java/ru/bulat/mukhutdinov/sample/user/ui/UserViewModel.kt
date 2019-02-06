package ru.bulat.mukhutdinov.sample.user.ui

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import ru.bulat.mukhutdinov.sample.infrastructure.common.ui.BaseViewModel
import ru.bulat.mukhutdinov.sample.infrastructure.util.data.DataStateLiveData
import ru.bulat.mukhutdinov.sample.user.model.User

interface UserViewModel : BaseViewModel {

    val user: ObservableField<User>

    val isSaveEnabled: ObservableBoolean

    val onSaveClicked: DataStateLiveData<Unit>

    fun onSaveClicked()
}