package ru.bulat.mukhutdinov.sample.infrastructure.common.ui

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseAndroidViewModel : ViewModel(), BaseViewModel {

    private val compositeDisposable = CompositeDisposable()

    protected fun Disposable.disposeOnCleared() {
        compositeDisposable.add(this)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}