package ru.bulat.mukhutdinov.mvvm.usersList.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ru.bulat.mukhutdinov.mvvm.R
import ru.bulat.mukhutdinov.mvvm.databinding.UsersItemBinding
import ru.bulat.mukhutdinov.mvvm.user.model.User
import ru.bulat.mukhutdinov.mvvm.usersList.ui.border.UsersListViewModel

class UsersAdapter(private val usersListViewModel: UsersListViewModel) : RecyclerView.Adapter<UserViewHolder>() {
    val users: MutableList<User> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: UsersItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.users_item, parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bindTo(users[position], usersListViewModel)
    }

    override fun getItemCount() =
        users.size
}