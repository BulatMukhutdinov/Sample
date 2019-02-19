package ru.bulat.mukhutdinov.sample.auth.usecase

import io.reactivex.Single

interface ValidateUsernameAndPasswordUseCase {

    fun execute(username: String, password: String): Single<Boolean>
}