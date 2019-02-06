package ru.bulat.mukhutdinov.sample.infrastructure.common.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.RecyclerView

open class BaseViewHolder<T>(
    view: View,
    private val clickListener: ((Int) -> Unit)?
) : RecyclerView.ViewHolder(view), LifecycleOwner {

    override fun getLifecycle(): Lifecycle = lifecycleRegistry

    private val lifecycleRegistry by lazy { LifecycleRegistry(this) }

    constructor(parent: ViewGroup, @LayoutRes layoutId: Int, clickListener: ((Int) -> Unit)? = null) :
        this(LayoutInflater.from(parent.context).inflate(layoutId, parent, false), clickListener)

    @CallSuper
    open fun bindTo(item: T?) {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)

        clickListener?.let {
            itemView.setOnClickListener { it(adapterPosition) }
        }
    }

    @CallSuper
    open fun onViewRecycled() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
    }
}