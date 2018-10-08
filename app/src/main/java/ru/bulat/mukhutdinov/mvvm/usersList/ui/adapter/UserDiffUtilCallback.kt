package ru.bulat.mukhutdinov.mvvm.usersList.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.bulat.mukhutdinov.mvvm.user.model.User

class UserDiffUtilCallback(private val oldList: List<User>, private val newList: List<User>)
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