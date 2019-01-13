package ru.bulat.mukhutdinov.sample.infrastructure.util.data

sealed class Either<out DataType, out ErrorType> {

    /** * Represents the loading side of [Either]. */
    data class Loading(override val isLoading: Boolean) : Either<Nothing, Nothing>()

    /** * Represents the error side of [Either] class which by convention is a "Failure". */
    data class Error<out ErrorType>(val error: ErrorType) : Either<Nothing, ErrorType>()

    /** * Represents the data side of [Either] class which by convention is a "Success". */
    data class Data<out DataType>(val data: DataType) : Either<DataType, Nothing>()

    /** * Represents a completion of a task without any result". */
    object Complete : Either<Nothing, Nothing>()

    open val isLoading get() = this is Loading
    val isData get() = this is Data<DataType>
    val isError get() = this is Error<ErrorType>

    fun loading(isLoading: Boolean) = Loading(isLoading)
    fun <ErrorType> error(error: ErrorType) = Error(error)
    fun <DataType> data(data: DataType) = Data(data)

    inline fun either(
        dataCallback: (DataType) -> Unit,
        noinline errorCallback: ((ErrorType) -> Unit)? = null,
        noinline loadingCallback: ((Boolean) -> Unit)? = null
    ) {
        when (this) {
            is Loading -> loadingCallback?.invoke(isLoading)
            is Error -> errorCallback?.invoke(error)
            is Data -> dataCallback(data)
        }
    }

    inline fun either(
        completeCallback: () -> Unit,
        noinline errorCallback: ((ErrorType) -> Unit)? = null,
        noinline loadingCallback: ((Boolean) -> Unit)? = null
    ) {
        when (this) {
            is Loading -> loadingCallback?.invoke(isLoading)
            is Error -> errorCallback?.invoke(error)
            is Complete -> completeCallback()
        }
    }
}