package ru.bulat.mukhutdinov.sample.infrastructure.util

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingRequestHelper
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import ru.bulat.mukhutdinov.sample.infrastructure.common.model.NetworkState
import ru.bulat.mukhutdinov.sample.infrastructure.exception.SampleException
import ru.bulat.mukhutdinov.sample.infrastructure.exception.mapLocalException

fun Completable.toEitherLiveData() = toFlowable<Nothing>().toEitherLiveData()
fun <T> Maybe<T>.toEitherLiveData() = toFlowable().toEitherLiveData()
fun <T> Single<T>.toEitherLiveData() = toFlowable().toEitherLiveData()
fun <T> Flowable<T>.toEitherLiveData(): LiveData<Either<T, SampleException>> =
    EitherLiveDataFromPublisher.fromPublisher(this) { it.mapLocalException() }

fun Context.toast(@StringRes stringRes: Int): Toast = Toast
    .makeText(this, getString(stringRes), Toast.LENGTH_LONG)
    .apply { show() }

private fun getErrorMessage(report: PagingRequestHelper.StatusReport): String {
    return PagingRequestHelper.RequestType.values().mapNotNull {
        report.getErrorFor(it)?.message
    }.first()
}

fun PagingRequestHelper.createStatusLiveData(): LiveData<NetworkState> {
    val liveData = MutableLiveData<NetworkState>()
    addListener { report ->
        when {
            report.hasRunning() -> liveData.postValue(NetworkState.Loading)
            report.hasError() -> liveData.postValue(
                NetworkState.Error(getErrorMessage(report)))
            else -> liveData.postValue(NetworkState.Loaded)
        }
    }
    return liveData
}