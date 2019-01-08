package ru.bulat.mukhutdinov.sample.infrastructure.util

import androidx.recyclerview.widget.DiffUtil
import ru.bulat.mukhutdinov.sample.infrastructure.common.model.BaseModel

class DiffUtilCallback<T : BaseModel>(private val oldList: List<T>, private val newList: List<T>)
    : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser = oldList[oldItemPosition]
        val newUser = newList[newItemPosition]
        return oldUser.id === newUser.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser = oldList[oldItemPosition]
        val newUser = newList[newItemPosition]
        return oldUser == newUser
    }
}