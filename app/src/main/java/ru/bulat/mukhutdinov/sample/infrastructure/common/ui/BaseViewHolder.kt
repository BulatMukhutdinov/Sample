package ru.bulat.mukhutdinov.sample.infrastructure.common.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView

open class BaseViewHolder<T>(
    view: View
) : RecyclerView.ViewHolder(view) {

    open fun onBind(item: T) {
    }

    open fun onViewRecycled() {
    }
}