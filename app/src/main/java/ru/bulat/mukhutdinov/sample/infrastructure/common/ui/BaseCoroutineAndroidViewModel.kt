package ru.bulat.mukhutdinov.sample.infrastructure.common.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.bulat.mukhutdinov.sample.infrastructure.util.data.DataStateLiveData
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class BaseCoroutineAndroidViewModel : ViewModel(), BaseViewModel {

    @JvmName("postToDataStateFlowable")
    fun <T> DataStateLiveData<T>.postToDataState(channel: suspend () -> ReceiveChannel<T>, context: CoroutineContext = EmptyCoroutineContext) {
        this.onStart()
        GlobalScope.launch {
            // todo  GlobalScope.launch remove after https://github.com/googlecodelabs/android-lifecycles/issues/55 or https://github.com/googlecodelabs/kotlin-coroutines/issues/23 will be fixed
            viewModelScope.launch(context) {
                try {
                    channel().consumeEach { data ->
                        withContext(Dispatchers.Main) {
                            this@postToDataState.onNext(data)
                        }
                    }
                } catch (t: Throwable) {
                    withContext(Dispatchers.Main) {
                        this@postToDataState.onError(t)
                    }
                }
            }
        }
    }

    @JvmName("postToDataStateSingle")
    fun <T> DataStateLiveData<T>.postToDataState(load: suspend () -> T, context: CoroutineContext = EmptyCoroutineContext) {
        this.onStart()
        viewModelScope.launch(context) {
            try {
                withContext(Dispatchers.Main) {
                    this@postToDataState.onNext(load())
                }
            } catch (t: Throwable) {
                withContext(Dispatchers.Main) {
                    this@postToDataState.onError(t)
                }
            }
        }
    }

    @JvmName("postToDataStateCompletable")
    fun DataStateLiveData<Unit>.postToDataState(load: suspend () -> Unit, context: CoroutineContext = viewModelScope.coroutineContext) {
        this.onStart()
        viewModelScope.launch(context) {
            try {
                load()
                withContext(Dispatchers.Main) {
                    this@postToDataState.onComplete()
                }
            } catch (t: Throwable) {
                withContext(Dispatchers.Main) {
                    this@postToDataState.onError(t)
                }
            }
        }
    }
}