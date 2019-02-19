package ru.bulat.mukhutdinov.sample.auth.usecase

import io.reactivex.Single

class ValidateUsernameAndPasswordInteractor : ValidateUsernameAndPasswordUseCase {

    override fun execute(username: String, password: String): Single<Boolean> =
            Single.fromCallable {
                username.isNotBlank() && password.isNotBlank()
            }
}