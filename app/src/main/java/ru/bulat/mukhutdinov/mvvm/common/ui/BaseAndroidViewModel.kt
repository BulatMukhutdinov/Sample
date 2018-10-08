package ru.bulat.mukhutdinov.mvvm.common.ui

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseAndroidViewModel<V : BaseView>(override var view: V) : ViewModel(), BaseViewModel<V> {

    protected val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}