package ru.bulat.mukhutdinov.sample.diary.usecase

import kotlinx.coroutines.channels.ReceiveChannel
import ru.bulat.mukhutdinov.sample.diary.gateway.NoteGateway

class ObserveNotesInteractor(private val noteGateway: NoteGateway) : ObserveNotesUseCase {

    override suspend fun execute(): ReceiveChannel<List<Any>> =
        noteGateway.observeNotes()
}