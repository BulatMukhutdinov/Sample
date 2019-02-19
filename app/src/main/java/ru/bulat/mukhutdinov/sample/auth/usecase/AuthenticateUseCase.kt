package ru.bulat.mukhutdinov.sample.auth.usecase

import io.reactivex.Single

interface AuthenticateUseCase {

    fun execute(username: String, passwordHash: String): Single<String>
}