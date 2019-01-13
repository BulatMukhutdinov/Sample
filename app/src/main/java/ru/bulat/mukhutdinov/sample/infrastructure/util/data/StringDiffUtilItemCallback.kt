package ru.bulat.mukhutdinov.sample.infrastructure.util.data

import androidx.recyclerview.widget.DiffUtil

object StringDiffUtilItemCallback : DiffUtil.ItemCallback<String>() {

    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
        oldItem == newItem
}