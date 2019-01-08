package ru.bulat.mukhutdinov.sample.userslist.ui

import androidx.lifecycle.LifecycleOwner
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.bulat.mukhutdinov.sample.infrastructure.common.ui.BaseAndroidViewModel
import ru.bulat.mukhutdinov.sample.infrastructure.exception.SampleException
import ru.bulat.mukhutdinov.sample.infrastructure.util.Either
import ru.bulat.mukhutdinov.sample.infrastructure.util.SingleLiveEvent
import ru.bulat.mukhutdinov.sample.infrastructure.util.toEitherLiveData
import ru.bulat.mukhutdinov.sample.user.gateway.UserLocalGateway
import ru.bulat.mukhutdinov.sample.user.model.User

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