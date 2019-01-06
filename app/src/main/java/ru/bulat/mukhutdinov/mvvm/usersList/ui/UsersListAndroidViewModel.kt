package ru.bulat.mukhutdinov.mvvm.usersList.ui

import androidx.lifecycle.LifecycleOwner
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.bulat.mukhutdinov.mvvm.infrastructure.common.ui.BaseAndroidViewModel
import ru.bulat.mukhutdinov.mvvm.infrastructure.util.SingleLiveEvent
import ru.bulat.mukhutdinov.mvvm.infrastructure.util.toEitherLiveData
import ru.bulat.mukhutdinov.mvvm.user.gateway.UserLocalGateway
import ru.bulat.mukhutdinov.mvvm.user.model.User

class UsersListAndroidViewModel(userLocalGateway: UserLocalGateway)
    : BaseAndroidViewModel(), UsersListViewModel {

    override val onUserClicked = SingleLiveEvent<User>()

    override lateinit var lifecycleOwner: LifecycleOwner

    override val users =
        userLocalGateway.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .toEitherLiveData()

    override fun onUserClicked(user: User) =
        onUserClicked.postValue(user)
}