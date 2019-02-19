package ru.bulat.mukhutdinov.sample.auth.service

import android.accounts.*
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.text.TextUtils
import org.koin.android.ext.android.inject
import ru.bulat.mukhutdinov.sample.auth.gateway.AuthApi
import ru.bulat.mukhutdinov.sample.auth.gateway.AuthApi.Companion.DEFAULT_AUTH_TOKEN_TYPE
import ru.bulat.mukhutdinov.sample.auth.ui.authenticator.AuthenticatorActivity

/**
 * Right solution is to place the service in one small, special-purpose APK.
 * When an app wishes to use your custom account type, it can check the device to see if your custom account service is available.
 * If not, it can direct the user to Google Play to download the service.
 * This may seem like a great deal of trouble at first, but compared with the alternative of re-entering credentials for every app that uses your custom account, it's refreshingly easy.
 */
class AccountService : Service() {

    private val authApi: AuthApi by inject()

    private lateinit var authenticator: Authenticator

    override fun onCreate() {
        super.onCreate()
        authenticator = Authenticator(this)
    }

    override fun onBind(intent: Intent?): IBinder? =
            authenticator.iBinder

    inner class Authenticator(private val context: Context) : AbstractAccountAuthenticator(context) {

        @Throws(NetworkErrorException::class)
        override fun addAccount(response: AccountAuthenticatorResponse,
                                accountType: String,
                                authTokenType: String?,
                                requiredFeatures: Array<String>?,
                                options: Bundle?
        ): Bundle? {
            val intent = Intent(context, AuthenticatorActivity::class.java)
            intent.putExtra(AuthenticatorActivity.EXTRA_ACCOUNT_TYPE, accountType)
            intent.putExtra(AuthenticatorActivity.EXTRA_IS_ADDING_NEW_ACCOUNT, true)
            intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response)
            intent.putExtra(AuthenticatorActivity.EXTRA_AUTH_TOKEN_TYPE, authTokenType
                    ?: DEFAULT_AUTH_TOKEN_TYPE)

            val bundle = Bundle()
            bundle.putParcelable(AccountManager.KEY_INTENT, intent)
            return bundle
        }

        override fun confirmCredentials(response: AccountAuthenticatorResponse,
                                        account: Account,
                                        options: Bundle
        ): Bundle? = null

        override fun editProperties(response: AccountAuthenticatorResponse,
                                    accountType: String
        ): Bundle = throw UnsupportedOperationException()

        override fun getAuthToken(response: AccountAuthenticatorResponse,
                                  account: Account,
                                  authTokenType: String,
                                  loginOptions: Bundle
        ): Bundle {
            // Extract the username and password from the Account Manager, and ask
            // the server for an appropriate AuthToken.
            val accountManager = AccountManager.get(context)

            var authToken = accountManager.peekAuthToken(account, authTokenType)

            // Lets give another try to authenticate the user
            if (TextUtils.isEmpty(authToken)) {
                val password = accountManager.getPassword(account)
                if (password != null) {
                    authToken = authApi.authenticate(account.name, password).blockingGet()
                }
            }

            // If we get an authToken - we return it
            if (!TextUtils.isEmpty(authToken)) {
                val result = Bundle()
                result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name)
                result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type)
                result.putString(AccountManager.KEY_AUTHTOKEN, authToken)
                return result
            }

            // If we get here, then we couldn't access the user's password - so we
            // need to re-prompt them for their credentials. We do that by creating
            // an intent to display our AuthenticatorActivity.
            val intent = Intent(context, AuthenticatorActivity::class.java)
            intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response)
            intent.putExtra(AuthenticatorActivity.EXTRA_ACCOUNT_TYPE, account.type)
            intent.putExtra(AuthenticatorActivity.EXTRA_AUTH_TOKEN_TYPE, authTokenType)

            val bundle = Bundle()
            bundle.putParcelable(AccountManager.KEY_INTENT, intent)
            return bundle
        }

        override fun getAuthTokenLabel(authTokenType: String): String? {
            // null means we don't support multiple authToken types
            return null
        }

        override fun hasFeatures(response: AccountAuthenticatorResponse,
                                 account: Account,
                                 features: Array<String>
        ): Bundle {
            // This call is used to query whether the Authenticator supports
            // specific features. We don't expect to get called, so we always
            // return false for any queries.
            val result = Bundle()
            result.putBoolean(AccountManager.KEY_BOOLEAN_RESULT, false)
            return result
        }

        override fun updateCredentials(response: AccountAuthenticatorResponse,
                                       account: Account,
                                       authTokenType: String,
                                       loginOptions: Bundle
        ): Bundle? = null
    }

    companion object {
        const val ACCOUNT_TYPE = "ru.bulat.mukhutdinov.sample"
    }
}