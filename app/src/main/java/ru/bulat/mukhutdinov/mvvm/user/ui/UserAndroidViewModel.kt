package ru.bulat.mukhutdinov.mvvm.user.ui

import androidx.databinding.ObservableField
import androidx.lifecycle.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.bulat.mukhutdinov.mvvm.common.ui.BaseAndroidViewModel
import ru.bulat.mukhutdinov.mvvm.user.gateway.UserLocalGateway
import ru.bulat.mukhutdinov.mvvm.user.model.User
import ru.bulat.mukhutdinov.mvvm.user.ui.contract.UserView
import ru.bulat.mukhutdinov.mvvm.user.ui.contract.UserViewModel
import timber.log.Timber

class UserAndroidViewModel(view: UserView, userId: String, private val userLocalGateway: UserLocalGateway)
    : BaseAndroidViewModel<UserView>(view), UserViewModel {

    override var user: ObservableField<User> = ObservableField()

    private var liveUser = userLocalGateway.findById(userId)

    init {
        liveUser.observe(view.lifecycleOwner, Observer { user ->
            this.user.set(user)
        })
    }

    override fun onSaveClicked() {
        user.get()?.let {
            compositeDisposable.add(userLocalGateway.update(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { view.navigateUp() },
                    { Timber.e(it) }
                ))
        }
    }

    companion object {
        const val USER_ID_EXTRA = "USER_ID_EXTRA"
    }
}