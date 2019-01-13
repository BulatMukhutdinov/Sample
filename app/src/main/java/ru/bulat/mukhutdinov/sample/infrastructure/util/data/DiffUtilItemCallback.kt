package ru.bulat.mukhutdinov.sample.infrastructure.util.data

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import ru.bulat.mukhutdinov.sample.infrastructure.common.model.BaseModel

class DiffUtilItemCallback<T : BaseModel> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
        oldItem.id == newItem.id

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
        oldItem == newItem
}