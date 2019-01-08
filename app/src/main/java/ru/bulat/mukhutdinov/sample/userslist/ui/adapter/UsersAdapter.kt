package ru.bulat.mukhutdinov.sample.userslist.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.bulat.mukhutdinov.sample.user.model.User
import ru.bulat.mukhutdinov.sample.userslist.ui.UsersListViewModel

class UsersAdapter(private val usersListViewModel: UsersListViewModel) : RecyclerView.Adapter<UserViewHolder>() {
    val users: MutableList<User> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder.create(parent)

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bindTo(users[position], usersListViewModel)
    }

    override fun getItemCount() =
        users.size
}