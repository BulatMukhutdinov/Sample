package ru.bulat.mukhutdinov.sample.user.ui

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.bulat.mukhutdinov.sample.infrastructure.common.ui.BaseAndroidViewModel
import ru.bulat.mukhutdinov.sample.infrastructure.exception.mapLocalException
import ru.bulat.mukhutdinov.sample.infrastructure.util.data.DataStateLiveData
import ru.bulat.mukhutdinov.sample.user.gateway.UserGateway
import ru.bulat.mukhutdinov.sample.user.model.User
import timber.log.Timber

class UserAndroidViewModel(userId: Long, private val userGateway: UserGateway)
    : BaseAndroidViewModel(), UserViewModel {

    override val user: ObservableField<User> = ObservableField()

    override val isSaveEnabled: ObservableBoolean = ObservableBoolean(true)

    override val onSaveClicked = DataStateLiveData<Unit>()

    init {
        userGateway
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
            userGateway.update(it)
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