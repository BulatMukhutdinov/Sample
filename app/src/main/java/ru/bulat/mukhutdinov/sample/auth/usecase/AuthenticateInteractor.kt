package ru.bulat.mukhutdinov.sample.auth.usecase

import io.reactivex.Single
import ru.bulat.mukhutdinov.sample.auth.gateway.AuthApi

class AuthenticateInteractor(private val authApi: AuthApi) : AuthenticateUseCase {

    override fun execute(username: String, passwordHash: String): Single<String> =
        authApi.authenticate(username, passwordHash)
}