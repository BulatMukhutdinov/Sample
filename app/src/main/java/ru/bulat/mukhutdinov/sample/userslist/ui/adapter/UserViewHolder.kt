package ru.bulat.mukhutdinov.sample.userslist.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ru.bulat.mukhutdinov.sample.R
import ru.bulat.mukhutdinov.sample.databinding.UsersItemBinding
import ru.bulat.mukhutdinov.sample.user.model.User
import ru.bulat.mukhutdinov.sample.userslist.ui.UsersListViewModel

class UserViewHolder(private val binding: UsersItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindTo(user: User, usersListViewModel: UsersListViewModel) {
        binding.user = user
        binding.usersListViewModel = usersListViewModel
        binding.icon.clipToOutline = true

        binding.executePendingBindings()
    }

    companion object {
        fun create(parent: ViewGroup): UserViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding: UsersItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.users_item, parent, false)
            return UserViewHolder(binding)
        }
    }
}