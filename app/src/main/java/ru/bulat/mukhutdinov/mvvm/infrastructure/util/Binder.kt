package ru.bulat.mukhutdinov.mvvm.infrastructure.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.bulat.mukhutdinov.mvvm.R
import ru.bulat.mukhutdinov.mvvm.infrastructure.exception.MvvmException
import ru.bulat.mukhutdinov.mvvm.user.model.User
import ru.bulat.mukhutdinov.mvvm.usersList.ui.UsersListViewModel
import ru.bulat.mukhutdinov.mvvm.usersList.ui.adapter.UserDiffUtilCallback
import ru.bulat.mukhutdinov.mvvm.usersList.ui.adapter.UsersAdapter

@BindingAdapter("android:src")
fun bind(view: ImageView, url: String?) {
    if (url.isNullOrBlank()) {
        view.setImageResource(R.color.gray)
    } else {
        Picasso.get().load(url)
            .placeholder(R.color.gray)
            .fit()
            .centerCrop()
            .noFade()
            .into(view)
    }
}

@BindingAdapter("app:usersListViewModel")
fun bind(recyclerView: RecyclerView, usersListViewModel: UsersListViewModel) {
    val adapter = UsersAdapter(usersListViewModel)

    recyclerView.adapter = adapter
    recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)

    usersListViewModel.users.observe(
        usersListViewModel.lifecycleOwner,
        Observer<Either<List<User>, MvvmException>> { either ->
            either?.either(
                dataCallback = { usersList ->
                    val userDiffUtilCallback = UserDiffUtilCallback(adapter.users, usersList)
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