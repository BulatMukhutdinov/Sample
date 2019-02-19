package ru.bulat.mukhutdinov.customsharedaccount

import android.accounts.AccountManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val accountManager = AccountManager.get(this)
        val accounts = accountManager.getAccountsByType("ru.bulat.mukhutdinov.sample")
        if (accounts.isNotEmpty()) {
            val account = accounts[0]
            text.text = getString(R.string.shared_account, account.name)
        } else {
            text.text = getString(R.string.no_shared_account)
        }
    }
}
