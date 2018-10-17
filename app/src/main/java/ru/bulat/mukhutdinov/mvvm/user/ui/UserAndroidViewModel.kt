package ru.bulat.mukhutdinov.mvvm.user.ui

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.bulat.mukhutdinov.mvvm.infrastructure.common.ui.BaseAndroidViewModel
import ru.bulat.mukhutdinov.mvvm.user.gateway.UserLocalGateway
import ru.bulat.mukhutdinov.mvvm.user.model.User
import timber.log.Timber

class UserAndroidViewModel(userId: String, private val userLocalGateway: UserLocalGateway)
    : BaseAndroidViewModel(), UserViewModel {

    override val user: ObservableField<User> = ObservableField()

    override val onSaveClicked = MutableLiveData<Unit>()

    init {
        compositeDisposable.add(userLocalGateway
            .findById(userId)
            .subscribe(
                { this.user.set(it) },
                { Timber.e(it) }
            ))
    }

    override fun onSaveClicked() {
        user.get()?.let {
            compositeDisposable.add(userLocalGateway.update(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { onSaveClicked.postValue(Unit) },
                    { Timber.e(it) }
                ))
        }
    }
}