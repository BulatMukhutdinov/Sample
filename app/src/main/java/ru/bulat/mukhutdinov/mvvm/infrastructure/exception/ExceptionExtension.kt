package ru.bulat.mukhutdinov.mvvm.infrastructure.exception

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

fun Completable.mapLocalExceptions(): Completable =
    this.onErrorResumeNext {
        Completable.error(MvvmException.DatabaseException(it))
    }

inline fun <reified T> Single<T>.mapLocalExceptions(): Single<T> =
    this.onErrorResumeNext {
        Single.error(MvvmException.DatabaseException(it))
    }

inline fun <reified T> Maybe<T>.mapLocalExceptions(): Maybe<T> =
    onErrorResumeNext { e: Throwable -> Maybe.error(MvvmException.DatabaseException(e)) }

inline fun <reified T> Flowable<T>.mapLocalExceptions(): Flowable<T> =
    onErrorResumeNext { e: Throwable -> Flowable.error(MvvmException.DatabaseException(e)) }

fun Throwable.mapLocalException(): MvvmException =
    this as? MvvmException ?: MvvmException.UnknownException(this)