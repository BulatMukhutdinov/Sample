package ru.bulat.mukhutdinov.sample.userslist.ui

import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.bulat.mukhutdinov.sample.R
import ru.bulat.mukhutdinov.sample.infrastructure.common.ui.BaseAndroidViewModel
import ru.bulat.mukhutdinov.sample.infrastructure.exception.SampleException
import ru.bulat.mukhutdinov.sample.infrastructure.extension.toEitherLiveData
import ru.bulat.mukhutdinov.sample.infrastructure.extension.toast
import ru.bulat.mukhutdinov.sample.infrastructure.util.data.DiffUtilCallback
import ru.bulat.mukhutdinov.sample.infrastructure.util.data.Either
import ru.bulat.mukhutdinov.sample.infrastructure.util.data.SingleLiveEvent
import ru.bulat.mukhutdinov.sample.user.gateway.UserLocalGateway
import ru.bulat.mukhutdinov.sample.user.model.User
import ru.bulat.mukhutdinov.sample.userslist.ui.adapter.UsersAdapter

class UsersListAndroidViewModel(userLocalGateway: UserLocalGateway)
    : BaseAndroidViewModel(), UsersListViewModel {

    override val onUserClicked = SingleLiveEvent<Either<User, SampleException>>()

    override lateinit var lifecycleOwner: LifecycleOwner

    override val users =
        userLocalGateway.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .toEitherLiveData()

    override fun onUserClicked(user: User) =
        onUserClicked.postValue(Either.Data(user))
}

@BindingAdapter("app:usersListViewModel")
fun bind(recyclerView: RecyclerView, usersListViewModel: UsersListViewModel) {
    val adapter = UsersAdapter(usersListViewModel)

    recyclerView.adapter = adapter
    recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)

    usersListViewModel.users.observe(
        usersListViewModel.lifecycleOwner,
        Observer { either ->
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