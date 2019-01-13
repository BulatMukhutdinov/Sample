package ru.bulat.mukhutdinov.sample.user.ui

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.bulat.mukhutdinov.sample.infrastructure.common.ui.BaseAndroidViewModel
import ru.bulat.mukhutdinov.sample.infrastructure.exception.SampleException
import ru.bulat.mukhutdinov.sample.infrastructure.exception.mapLocalException
import ru.bulat.mukhutdinov.sample.infrastructure.util.data.Either
import ru.bulat.mukhutdinov.sample.user.gateway.UserLocalGateway
import ru.bulat.mukhutdinov.sample.user.model.User
import timber.log.Timber

class UserAndroidViewModel(userId: Long, private val userLocalGateway: UserLocalGateway)
    : BaseAndroidViewModel(), UserViewModel {

    override val user: ObservableField<User> = ObservableField()

    override val isSaveEnabled: ObservableBoolean = ObservableBoolean(true)

    override val onSaveClicked = MutableLiveData<Either<Unit, SampleException>>()

    init {
        compositeDisposable.add(userLocalGateway
            .findById(userId)
            .subscribeOn(Schedulers.io())
            .subscribe(
                { this.user.set(it) },
                { Timber.e(it) }
            ))
    }

    override fun onSaveClicked() {
        user.get()?.let {
            compositeDisposable.add(userLocalGateway.update(it)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    onSaveClicked.postValue(Either.Loading(true))
                    isSaveEnabled.set(false)
                }
                .doOnTerminate {
                    onSaveClicked.postValue(Either.Loading(false))
                    isSaveEnabled.set(true)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        onSaveClicked.postValue(Either.Complete)
                    },
                    { error ->
                        onSaveClicked.postValue(Either.Error(error.mapLocalException()))
                        Timber.e(error)
                    }
                ))
        }
    }
}