package ru.bulat.mukhutdinov.sample

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var loading: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loading = findViewById(R.id.loading)
        setSupportActionBar(findViewById(R.id.toolbar))
    }

    fun setLoadingVisible(isVisible: Boolean) {
        if (isVisible) {
            loading.visibility = View.VISIBLE
        } else {
            loading.visibility = View.GONE
        }
    }
}