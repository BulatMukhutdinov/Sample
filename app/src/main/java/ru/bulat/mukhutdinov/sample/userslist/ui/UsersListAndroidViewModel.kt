package ru.bulat.mukhutdinov.sample.userslist.ui

import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.bulat.mukhutdinov.sample.R
import ru.bulat.mukhutdinov.sample.infrastructure.common.ui.BaseAndroidViewModel
import ru.bulat.mukhutdinov.sample.infrastructure.extension.observeViewState
import ru.bulat.mukhutdinov.sample.infrastructure.extension.postToViewStateLiveData
import ru.bulat.mukhutdinov.sample.infrastructure.extension.toast
import ru.bulat.mukhutdinov.sample.infrastructure.util.data.DiffUtilCallback
import ru.bulat.mukhutdinov.sample.infrastructure.util.data.DataStateLiveData
import ru.bulat.mukhutdinov.sample.user.gateway.UserLocalGateway
import ru.bulat.mukhutdinov.sample.user.model.User
import ru.bulat.mukhutdinov.sample.userslist.ui.adapter.UsersAdapter

class UsersListAndroidViewModel(userLocalGateway: UserLocalGateway)
    : BaseAndroidViewModel(), UsersListViewModel {

    override val onUserClicked = DataStateLiveData.createForSingle<User>()

    override lateinit var lifecycleOwner: LifecycleOwner

    override val users = DataStateLiveData<List<User>>()

    init {
        userLocalGateway.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .postToViewStateLiveData(users)
            .disposeOnCleared()
    }

    override fun onUserClicked(user: User) =
        onUserClicked.onNext(user)
}

@BindingAdapter("app:usersListViewModel")
fun bind(recyclerView: RecyclerView, usersListViewModel: UsersListViewModel) {
    val adapter = UsersAdapter(usersListViewModel)

    recyclerView.adapter = adapter
    recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)

    usersListViewModel.users.observeViewState(
        owner = usersListViewModel.lifecycleOwner,
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