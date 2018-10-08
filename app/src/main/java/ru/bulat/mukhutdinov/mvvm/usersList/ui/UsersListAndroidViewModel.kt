package ru.bulat.mukhutdinov.mvvm.usersList.ui

import androidx.core.os.bundleOf
import androidx.lifecycle.LifecycleOwner
import ru.bulat.mukhutdinov.mvvm.R
import ru.bulat.mukhutdinov.mvvm.common.ui.BaseAndroidViewModel
import ru.bulat.mukhutdinov.mvvm.user.gateway.UserLocalGateway
import ru.bulat.mukhutdinov.mvvm.user.model.User
import ru.bulat.mukhutdinov.mvvm.user.ui.UserAndroidViewModel.Companion.USER_ID_EXTRA
import ru.bulat.mukhutdinov.mvvm.usersList.ui.border.UsersListView
import ru.bulat.mukhutdinov.mvvm.usersList.ui.border.UsersListViewModel

class UsersListAndroidViewModel(view: UsersListView, userLocalGateway: UserLocalGateway)
    : BaseAndroidViewModel<UsersListView>(view), UsersListViewModel {


    override val lifecycleOwner: LifecycleOwner
        get() = view.lifecycleOwner

    override val users = userLocalGateway.getAll()

    override fun onUserClicked(user: User) {
        val bundle = bundleOf(
            USER_ID_EXTRA to user.id
        )

        view.navigateTo(R.id.action_usersListFragment_to_userFragment, bundle)
    }
}