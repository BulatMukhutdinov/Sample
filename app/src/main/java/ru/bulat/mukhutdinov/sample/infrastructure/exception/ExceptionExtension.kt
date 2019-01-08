package ru.bulat.mukhutdinov.sample.infrastructure.exception

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

fun Completable.mapLocalExceptions(): Completable =
    this.onErrorResumeNext {
        Completable.error(SampleException.DatabaseException(it))
    }

inline fun <reified T> Single<T>.mapLocalExceptions(): Single<T> =
    this.onErrorResumeNext {
        Single.error(SampleException.DatabaseException(it))
    }

inline fun <reified T> Maybe<T>.mapLocalExceptions(): Maybe<T> =
    onErrorResumeNext { e: Throwable -> Maybe.error(SampleException.DatabaseException(e)) }

inline fun <reified T> Flowable<T>.mapLocalExceptions(): Flowable<T> =
    onErrorResumeNext { e: Throwable -> Flowable.error(SampleException.DatabaseException(e)) }

fun Throwable.mapLocalException(): SampleException =
    this as? SampleException ?: SampleException.UnknownException(this)