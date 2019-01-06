package ru.bulat.mukhutdinov.mvvm.infrastructure.util

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import ru.bulat.mukhutdinov.mvvm.infrastructure.exception.MvvmException
import ru.bulat.mukhutdinov.mvvm.infrastructure.exception.mapLocalException

fun Completable.toEitherLiveData() = toFlowable<Nothing>().toEitherLiveData()
fun <T> Maybe<T>.toEitherLiveData() = toFlowable().toEitherLiveData()
fun <T> Single<T>.toEitherLiveData() = toFlowable().toEitherLiveData()
fun <T> Flowable<T>.toEitherLiveData(): LiveData<Either<T, MvvmException>> =
    EitherLiveDataFromPublisher.fromPublisher(this) { it.mapLocalException() }

fun Context.toast(@StringRes stringRes: Int): Toast = Toast
    .makeText(this, getString(stringRes), Toast.LENGTH_LONG)
    .apply { show() }