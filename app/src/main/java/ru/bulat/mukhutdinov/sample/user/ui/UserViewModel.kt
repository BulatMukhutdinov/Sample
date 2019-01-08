package ru.bulat.mukhutdinov.sample.user.ui

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import ru.bulat.mukhutdinov.sample.infrastructure.common.ui.BaseViewModel
import ru.bulat.mukhutdinov.sample.infrastructure.exception.SampleException
import ru.bulat.mukhutdinov.sample.infrastructure.util.Either
import ru.bulat.mukhutdinov.sample.user.model.User

interface UserViewModel : BaseViewModel {

    val user: ObservableField<User>

    val isSaveEnabled: ObservableBoolean

    val onSaveClicked: LiveData<Either<Unit, SampleException>>

    fun onSaveClicked()
}