package ru.bulat.mukhutdinov.sample.infrastructure.common.model

sealed class NetworkState(val status: Status) {

    object Loaded : NetworkState(Status.SUCCESS)

    object Loading : NetworkState(Status.RUNNING)

    class Error(val message: String?) : NetworkState(Status.RUNNING)
}

enum class Status {
    RUNNING,
    SUCCESS,
    FAILED
}