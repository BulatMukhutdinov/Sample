package ru.bulat.mukhutdinov.sample.post.usecase

import io.reactivex.Single

class ValidateTextPostInteractor : ValidateTextPostUseCase {

    override fun execute(title: String, body: String): Single<Boolean> =
            Single.fromCallable {
                title.isNotBlank() || body.isNotBlank()
            }
}