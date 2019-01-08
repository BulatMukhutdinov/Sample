package ru.bulat.mukhutdinov.sample.infrastructure.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import ru.bulat.mukhutdinov.sample.R

@BindingAdapter("android:src")
fun bind(view: ImageView, url: String?) {
    if (url.isNullOrBlank()) {
        view.setImageResource(R.color.gray)
    } else {
        Picasso.get().load(url)
            .placeholder(R.color.gray)
            .fit()
            .centerCrop()
            .noFade()
            .into(view)
    }
}