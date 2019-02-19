package ru.bulat.mukhutdinov.sample.auth.ui.authenticator

import android.accounts.Account
import android.accounts.AccountManager
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.bulat.mukhutdinov.sample.auth.service.AccountService
import ru.bulat.mukhutdinov.sample.auth.usecase.AuthenticateUseCase
import ru.bulat.mukhutdinov.sample.auth.usecase.HashPasswordUseCase
import ru.bulat.mukhutdinov.sample.auth.usecase.ValidateUsernameAndPasswordUseCase
import ru.bulat.mukhutdinov.sample.infrastructure.common.ui.BaseAndroidViewModel
import ru.bulat.mukhutdinov.sample.infrastructure.extension.postToViewStateLiveData
import ru.bulat.mukhutdinov.sample.infrastructure.util.data.DataStateLiveData

class AuthenticatorAndroidViewModel(
        private val hashPasswordUseCase: HashPasswordUseCase,
        private val validateUsernameAndPasswordUseCase: ValidateUsernameAndPasswordUseCase,
        private val authenticateUseCase: AuthenticateUseCase
) : BaseAndroidViewModel(), AuthenticatorViewModel {

    private var username: String = ""
    private var password: String = ""

    override val isSignInEnabled: DataStateLiveData<Boolean> = DataStateLiveData.create()

    override fun updateUsername(username: String) {
        this.username = username
        invalidateIsSignInEnabled()
    }

    override fun updatePassword(password: String) {
        this.password = password
        invalidateIsSignInEnabled()
    }

    private fun invalidateIsSignInEnabled() {
        validateUsernameAndPasswordUseCase.execute(username, password)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .postToViewStateLiveData(isSignInEnabled)
                .disposeOnCleared()
    }

    override fun createAccount(accountManager: AccountManager,
                               username: String,
                               password: String,
                               isAddingNewAccount: Boolean,
                               authTokenType: String
    ): DataStateLiveData<Unit> {
        val dataStateLiveData = DataStateLiveData.createForSingle<Unit>()

        hashPasswordUseCase.execute(password)
                .flatMap { passwordHash ->
                    authenticateUseCase
                            .execute(username, passwordHash)
                            .map { authToken -> authToken to passwordHash }
                }
                .flatMapCompletable { (authToken, passwordHash) ->
                    Completable.fromCallable {
                        val account = Account(username, AccountService.ACCOUNT_TYPE)
                        if (isAddingNewAccount) {
                            // Creating the account on the device and setting the auth token we got
                            // (Not setting the auth token will cause another call to the server to authenticate the user)
                            accountManager.addAccountExplicitly(account, passwordHash, null)
                            accountManager.setAuthToken(account, authTokenType, authToken)
                        } else {
                            accountManager.setPassword(account, passwordHash)
                        }
                    }
                }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .postToViewStateLiveData(dataStateLiveData)
                .disposeOnCleared()

        return dataStateLiveData
    }
}