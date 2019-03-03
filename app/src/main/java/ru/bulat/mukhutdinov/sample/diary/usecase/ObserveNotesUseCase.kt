package ru.bulat.mukhutdinov.sample.diary.usecase

import kotlinx.coroutines.channels.ReceiveChannel

interface ObserveNotesUseCase {

    suspend fun execute(): ReceiveChannel<List<Any>>
}