package ru.bulat.mukhutdinov.sample.user.ui

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.bulat.mukhutdinov.sample.infrastructure.common.ui.BaseAndroidViewModel
import ru.bulat.mukhutdinov.sample.infrastructure.exception.mapLocalException
import ru.bulat.mukhutdinov.sample.infrastructure.util.data.DataStateLiveData
import ru.bulat.mukhutdinov.sample.user.gateway.UserLocalGateway
import ru.bulat.mukhutdinov.sample.user.model.User
import timber.log.Timber

class UserAndroidViewModel(userId: Long, private val userLocalGateway: UserLocalGateway)
    : BaseAndroidViewModel(), UserViewModel {

    override val user: ObservableField<User> = ObservableField()

    override val isSaveEnabled: ObservableBoolean = ObservableBoolean(true)

    override val onSaveClicked = DataStateLiveData<Unit>()

    init {
        userLocalGateway
            .findById(userId)
            .subscribeOn(Schedulers.io())
            .subscribe(
                { this.user.set(it) },
                { Timber.e(it) }
            )
            .disposeOnCleared()
    }

    override fun onSaveClicked() {
        user.get()?.let {
            userLocalGateway.update(it)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    onSaveClicked.onStart()
                    isSaveEnabled.set(false)
                }
                .doOnTerminate {
                    isSaveEnabled.set(true)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        onSaveClicked.onComplete()
                    },
                    { error ->
                        onSaveClicked.onError(error.mapLocalException())
                        Timber.e(error)
                    }
                )
                .disposeOnCleared()
        }
    }
}