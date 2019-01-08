package ru.bulat.mukhutdinov.sample.infrastructure.exception

sealed class SampleException(message: String? = null, cause: Throwable? = null) : Exception(message, cause) {

    class DatabaseException(exception: Throwable) : SampleException(exception.message, exception)

    class UnknownException(exception: Throwable) : SampleException(exception.message, exception)
}