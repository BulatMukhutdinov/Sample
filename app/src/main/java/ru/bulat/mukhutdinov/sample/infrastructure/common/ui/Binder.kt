package ru.bulat.mukhutdinov.sample.infrastructure.common.ui

import android.app.Application
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import org.koin.android.ext.android.getKoin
import ru.bulat.mukhutdinov.sample.R

@BindingAdapter("android:src")
fun bind(view: ImageView, url: String?) {

    if (url.isNullOrBlank()) {
        view.setImageResource(R.color.gray)
    } else {
        val koin = (view.context.applicationContext as Application).getKoin()
        val picasso: Picasso = koin.get()

        picasso.load(url)
            .placeholder(R.color.gray)
            .into(view)
    }
}