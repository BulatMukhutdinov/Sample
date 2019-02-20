package ru.bulat.mukhutdinov.sample.post.usecase

import io.reactivex.Completable

interface CreateTextPostUseCase {

    fun execute(title: String, body: String, tags: List<String>): Completable
}