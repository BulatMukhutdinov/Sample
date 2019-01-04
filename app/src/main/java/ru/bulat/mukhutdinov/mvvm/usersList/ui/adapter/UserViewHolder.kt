package ru.bulat.mukhutdinov.mvvm.usersList.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import ru.bulat.mukhutdinov.mvvm.databinding.UsersItemBinding
import ru.bulat.mukhutdinov.mvvm.user.model.User
import ru.bulat.mukhutdinov.mvvm.usersList.ui.UsersListViewModel

class UserViewHolder(private val binding: UsersItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindTo(user: User, usersListViewModel: UsersListViewModel) {
        binding.user = user
        binding.usersListViewModel = usersListViewModel
        binding.icon.clipToOutline = true

        binding.executePendingBindings()
    }
}