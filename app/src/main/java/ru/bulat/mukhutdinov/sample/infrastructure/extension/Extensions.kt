package ru.bulat.mukhutdinov.sample.infrastructure.extension

import android.content.Context
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagingRequestHelper
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import ru.bulat.mukhutdinov.sample.infrastructure.common.model.NetworkState
import ru.bulat.mukhutdinov.sample.infrastructure.exception.SampleException
import ru.bulat.mukhutdinov.sample.infrastructure.util.data.DataState
import ru.bulat.mukhutdinov.sample.infrastructure.util.data.DataStateLiveData

fun <T> Flowable<T>.postToViewStateLiveData(targetLiveData: DataStateLiveData<T>): Disposable =
    doOnSubscribe { targetLiveData.onStart() }
        .doOnComplete { targetLiveData.onComplete() }
        .subscribe(
            { result -> targetLiveData.onNext(result) },
            { error -> targetLiveData.onError(error) }
        )

fun <T> Single<T>.postToViewStateLiveData(targetLiveData: DataStateLiveData<T>): Disposable =
    doOnSubscribe { targetLiveData.onStart() }
        .subscribe(
            { result -> targetLiveData.onNext(result) },
            { error -> targetLiveData.onError(error) }
        )

fun Completable.postToViewStateLiveData(targetLiveData: DataStateLiveData<Unit>): Disposable =
    doOnSubscribe { targetLiveData.onStart() }
        .subscribe(
            { targetLiveData.onComplete() },
            { error -> targetLiveData.onError(error) }
        )

fun <DataType> LiveData<DataState<DataType>>.observeViewState(
    owner: LifecycleOwner,
    dataCallback: ((DataType) -> Unit)? = null,
    errorCallback: ((SampleException) -> Unit)? = null,
    completeCallback: (() -> Unit)? = null,
    loadingCallback: ((Boolean) -> Unit)? = null
) {
    this.observe(owner,
        Observer { dataState ->
            dataState?.either(dataCallback, errorCallback, completeCallback, loadingCallback)
        })
}

fun Context.toast(@StringRes stringRes: Int): Toast = Toast
    .makeText(this, getString(stringRes), Toast.LENGTH_LONG)
    .apply { show() }

private fun getErrorMessage(report: PagingRequestHelper.StatusReport): String? {
    return PagingRequestHelper.RequestType.values().mapNotNull {
        report.getErrorFor(it)?.message
    }.firstOrNull()
}

fun PagingRequestHelper.createStatusLiveData(): LiveData<NetworkState> {
    val liveData = MutableLiveData<NetworkState>()
    addListener { report ->
        when {
            report.hasRunning() -> liveData.postValue(NetworkState.Loading)
            report.hasError() -> liveData.postValue(NetworkState.Error(getErrorMessage(report)))
            else -> liveData.postValue(NetworkState.Loaded)
        }
    }
    return liveData
}

fun View.show() {
    this.visibility = VISIBLE
}

fun View.hide() {
    this.visibility = GONE
}