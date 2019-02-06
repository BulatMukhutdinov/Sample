package ru.bulat.mukhutdinov.sample.infrastructure.util.data

sealed class State {

    object Loading : State()

    object Success : State()

    object Complete : State()

    class Error(val e: Throwable) : State()
}