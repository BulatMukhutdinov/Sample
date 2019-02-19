package ru.bulat.mukhutdinov.sample.auth.ui

import android.accounts.AccountManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.auth.*
import kotlinx.android.synthetic.main.auth_sign_in.*
import org.koin.androidx.viewmodel.ext.viewModel
import ru.bulat.mukhutdinov.sample.R
import ru.bulat.mukhutdinov.sample.auth.gateway.AuthApi
import ru.bulat.mukhutdinov.sample.auth.service.AccountService
import ru.bulat.mukhutdinov.sample.auth.ui.authenticator.AuthenticatorAndroidViewModel
import ru.bulat.mukhutdinov.sample.auth.ui.authenticator.AuthenticatorViewModel
import ru.bulat.mukhutdinov.sample.infrastructure.common.ui.BaseFragment
import ru.bulat.mukhutdinov.sample.infrastructure.extension.hide
import ru.bulat.mukhutdinov.sample.infrastructure.extension.observeViewState
import ru.bulat.mukhutdinov.sample.infrastructure.extension.show
import ru.bulat.mukhutdinov.sample.infrastructure.extension.toast

class AuthFragment : BaseFragment<AuthenticatorViewModel>() {

    override val viewModel: AuthenticatorViewModel by viewModel<AuthenticatorAndroidViewModel>()

    private lateinit var compositeDisposable: CompositeDisposable
    private lateinit var accountManager: AccountManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.auth, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        compositeDisposable = CompositeDisposable()

        accountManager = AccountManager.get(context)
        val accounts = accountManager.getAccountsByType(AccountService.ACCOUNT_TYPE)
        if (accounts.isEmpty()) {
            currentAccountLabel.hide()
            noCurrentAccountGroup.show()
        } else {
            showAccountLabel()
        }

        setupSignInClick()
        setupSignInState()
    }

    private fun setupSignInState() {
        viewModel.isSignInEnabled.observeViewState(
                owner = this,
                dataCallback = { signIn.isEnabled = it },
                errorCallback = { context?.toast(R.string.common_exception) }
        )

        compositeDisposable.add(
                usernameValue.textChanges()
                        .map { it.toString() }
                        .subscribe { viewModel.updateUsername(it) }
        )

        compositeDisposable.add(
                passwordValue.textChanges()
                        .map { it.toString() }
                        .subscribe { viewModel.updatePassword(it) }
        )
    }

    private fun setupSignInClick() {
        signIn.setOnClickListener {
            val username = usernameValue.text.toString()
            val password = passwordValue.text.toString()

            viewModel.createAccount(username = username,
                    accountManager = accountManager,
                    authTokenType = AuthApi.DEFAULT_AUTH_TOKEN_TYPE,
                    isAddingNewAccount = true,
                    password = password
            ).observeViewState(
                    owner = this,
                    completeCallback = { showAccountLabel() },
                    errorCallback = { context?.toast(R.string.common_exception) }
            )
        }
    }

    private fun showAccountLabel() {
        currentAccountLabel.show()
        noCurrentAccountGroup.hide()

        val accounts = accountManager.getAccountsByType(AccountService.ACCOUNT_TYPE)
        val sampleAccount = accounts[0]
        currentAccountLabel.text = getString(R.string.auth_current_account, sampleAccount.name)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.dispose()
    }
}