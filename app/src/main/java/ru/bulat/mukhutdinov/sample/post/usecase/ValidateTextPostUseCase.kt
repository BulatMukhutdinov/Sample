package ru.bulat.mukhutdinov.sample.post.usecase

import io.reactivex.Single

interface ValidateTextPostUseCase {

    fun execute(title: String, body: String): Single<Boolean>
}