package ru.bulat.mukhutdinov.sample.auth.usecase

import io.reactivex.Single

interface HashPasswordUseCase {

    fun execute(password: String): Single<String>
}