package ru.bulat.mukhutdinov.sample.auth.ui.authenticator

import android.accounts.AccountManager
import ru.bulat.mukhutdinov.sample.infrastructure.common.ui.BaseViewModel
import ru.bulat.mukhutdinov.sample.infrastructure.util.data.DataStateLiveData

interface AuthenticatorViewModel : BaseViewModel {

    val isSignInEnabled: DataStateLiveData<Boolean>

    fun updateUsername(username: String)

    fun updatePassword(password: String)

    fun createAccount(accountManager: AccountManager,
                      username: String,
                      password: String,
                      isAddingNewAccount: Boolean,
                      authTokenType: String
    ): DataStateLiveData<Unit>
}