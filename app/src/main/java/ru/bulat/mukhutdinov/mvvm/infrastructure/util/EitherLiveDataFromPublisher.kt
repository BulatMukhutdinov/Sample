package ru.bulat.mukhutdinov.mvvm.infrastructure.util

import androidx.lifecycle.LiveData
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import java.util.concurrent.atomic.AtomicReference

object EitherLiveDataFromPublisher {
    fun <DataType, ErrorType : Throwable> fromPublisher(
        publisher: Publisher<DataType>,
        mapException: (Throwable) -> ErrorType
    ): LiveData<Either<DataType, ErrorType>> = PublisherLiveData(publisher, mapException)

    private class PublisherLiveData<DataType, ErrorType : Throwable>(
        private val publisher: Publisher<DataType>,
        private val mapException: (Throwable) -> ErrorType
    ) : LiveData<Either<DataType, ErrorType>>() {

        internal val subscriber: AtomicReference<LiveDataSubscriber> = AtomicReference()

        override fun onActive() {
            super.onActive()
            val liveDataSubscriber = LiveDataSubscriber()
            subscriber.set(liveDataSubscriber)
            publisher.subscribe(liveDataSubscriber)
        }

        override fun onInactive() {
            super.onInactive()
            val subscriber = subscriber.getAndSet(null)
            subscriber?.cancelSubscription()
        }

        inner class LiveDataSubscriber : AtomicReference<Subscription>(), Subscriber<DataType> {

            override fun onSubscribe(subscription: Subscription) {
                if (compareAndSet(null, subscription)) {
                    subscription.request(java.lang.Long.MAX_VALUE)
                    value = Either.Loading(true)
                } else {
                    subscription.cancel()
                }
            }

            override fun onNext(item: DataType) {
                value = Either.Data(item)
            }

            override fun onError(ex: Throwable) {
                subscriber.compareAndSet(this, null)

                value = Either.Loading(false)

                value = Either.Error(mapException(ex))
            }

            override fun onComplete() {
                subscriber.compareAndSet(this, null)

                value = Either.Loading(false)

                value = Either.Complete
            }

            fun cancelSubscription() {
                val subscription = get()
                subscription?.cancel()
            }
        }
    }
}