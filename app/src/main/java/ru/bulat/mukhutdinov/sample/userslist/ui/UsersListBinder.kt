package ru.bulat.mukhutdinov.sample.userslist.ui

import androidx.databinding.BindingAdapter
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.bulat.mukhutdinov.sample.R
import ru.bulat.mukhutdinov.sample.infrastructure.exception.SampleException
import ru.bulat.mukhutdinov.sample.infrastructure.util.data.DiffUtilCallback
import ru.bulat.mukhutdinov.sample.infrastructure.util.data.Either
import ru.bulat.mukhutdinov.sample.infrastructure.util.toast
import ru.bulat.mukhutdinov.sample.user.model.User
import ru.bulat.mukhutdinov.sample.userslist.ui.adapter.UsersAdapter

@BindingAdapter("app:usersListViewModel")
fun bind(recyclerView: RecyclerView, usersListViewModel: UsersListViewModel) {
    val adapter = UsersAdapter(usersListViewModel)

    recyclerView.adapter = adapter
    recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)

    usersListViewModel.users.observe(
        usersListViewModel.lifecycleOwner,
        Observer<Either<List<User>, SampleException>> { either ->
            either?.either(
                dataCallback = { usersList ->
                    val userDiffUtilCallback = DiffUtilCallback(adapter.users, usersList)
                    val userDiffResult = DiffUtil.calculateDiff(userDiffUtilCallback)

                    adapter.users.clear()
                    adapter.users.addAll(usersList)
                    userDiffResult.dispatchUpdatesTo(adapter)
                },
                errorCallback = {
                    recyclerView.context.toast(R.string.common_exception)
                }
            )
        }
    )
}