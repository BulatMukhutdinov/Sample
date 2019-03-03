package ru.bulat.mukhutdinov.sample.diary.gateway

import kotlinx.coroutines.channels.ReceiveChannel
import ru.bulat.mukhutdinov.sample.diary.model.Note

interface NoteGateway {

    suspend fun add(note: Note)

    suspend fun observeNotes(): ReceiveChannel<List<Note>>
}