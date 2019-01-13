package ru.bulat.mukhutdinov.sample.infrastructure.common.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import org.kodein.di.Kodein
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import ru.bulat.mukhutdinov.sample.R

@BindingAdapter("android:src")
fun bind(view: ImageView, url: String?) {

    if (url.isNullOrBlank()) {
        view.setImageResource(R.color.gray)
    } else {
        val kodein: Kodein by closestKodein(view.context)
        val picasso: Picasso by kodein.instance()

        picasso.load(url)
            .placeholder(R.color.gray)
            .fit()
            .centerCrop()
            .into(view)
    }
}