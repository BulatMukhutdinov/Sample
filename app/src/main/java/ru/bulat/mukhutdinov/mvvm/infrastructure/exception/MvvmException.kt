package ru.bulat.mukhutdinov.mvvm.infrastructure.exception

sealed class MvvmException(message: String? = null, cause: Throwable? = null) : Exception(message, cause) {

    class DatabaseException(exception: Throwable) : MvvmException(exception.message, exception)

    class UnknownException(exception: Throwable) : MvvmException(exception.message, exception)
}