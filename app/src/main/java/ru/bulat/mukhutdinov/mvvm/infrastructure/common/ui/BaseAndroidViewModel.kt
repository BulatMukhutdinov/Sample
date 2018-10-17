package ru.bulat.mukhutdinov.mvvm.infrastructure.common.ui

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseAndroidViewModel : ViewModel(), BaseViewModel {

    protected val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}