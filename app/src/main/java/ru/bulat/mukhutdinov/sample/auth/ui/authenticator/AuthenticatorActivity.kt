package ru.bulat.mukhutdinov.sample.auth.ui.authenticator

import android.accounts.AccountAuthenticatorResponse
import android.accounts.AccountManager
import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.auth_sign_in.*
import org.koin.androidx.viewmodel.ext.viewModel
import ru.bulat.mukhutdinov.sample.R
import ru.bulat.mukhutdinov.sample.infrastructure.extension.observeViewState
import ru.bulat.mukhutdinov.sample.infrastructure.extension.toast

class AuthenticatorActivity : AppCompatActivity() {

    private val viewModel by viewModel<AuthenticatorAndroidViewModel>()

    private lateinit var accountManager: AccountManager

    private var accountAuthenticatorResponse: AccountAuthenticatorResponse? = null
    private var resultBundle: Bundle? = null

    private lateinit var compositeDisposable: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.authenticator_activity)
        compositeDisposable = CompositeDisposable()

        accountAuthenticatorResponse = intent.getParcelableExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE)
        accountAuthenticatorResponse?.onRequestContinued()

        accountManager = AccountManager.get(this)

        setupSignInClick()
        setupSignInState()
    }

    private fun setupSignInState() {
        viewModel.isSignInEnabled.observeViewState(
                owner = this,
                dataCallback = { signIn.isEnabled = it },
                errorCallback = {
                    this.toast(R.string.common_exception)
                    finish()
                }
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
                    authTokenType = intent.getStringExtra(EXTRA_AUTH_TOKEN_TYPE),
                    isAddingNewAccount = intent.getBooleanExtra(EXTRA_IS_ADDING_NEW_ACCOUNT, false),
                    password = password
            ).observeViewState(
                    owner = this,
                    completeCallback = {
                        resultBundle = intent.extras
                        setResult(Activity.RESULT_OK, intent)
                        finish()
                    },
                    errorCallback = {
                        this.toast(R.string.common_exception)
                        finish()
                    }
            )
        }
    }

    override fun finish() {
        accountAuthenticatorResponse?.let {
            // send the result bundle back if set, otherwise send an error.
            if (resultBundle != null) {
                it.onResult(resultBundle)
            } else {
                it.onError(AccountManager.ERROR_CODE_CANCELED, "canceled")
            }
            accountAuthenticatorResponse = null
        }

        super.finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    companion object {
        const val EXTRA_AUTH_TOKEN_TYPE = "auth_token_type_extra"
        const val EXTRA_IS_ADDING_NEW_ACCOUNT = "is_adding_new_account_extra"
        const val EXTRA_ACCOUNT_TYPE = "account_type_extra"
    }
}