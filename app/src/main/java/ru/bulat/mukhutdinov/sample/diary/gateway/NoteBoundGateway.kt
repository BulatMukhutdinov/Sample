package ru.bulat.mukhutdinov.sample.diary.gateway

import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.reactive.openSubscription
import ru.bulat.mukhutdinov.sample.diary.db.NoteDao
import ru.bulat.mukhutdinov.sample.diary.model.Note
import ru.bulat.mukhutdinov.sample.diary.model.NoteConverter
import timber.log.Timber

class NoteBoundGateway(private val noteDao: NoteDao) : NoteGateway {

    override suspend fun observeNotes(): ReceiveChannel<List<Note>> =
        noteDao
            .getAll()
            .doOnSubscribe { Timber.e("############# ${Thread.currentThread().name}") }
            .map { NoteConverter.fromDatabase(it) }
            .openSubscription()

    override suspend fun add(note: Note) {
        noteDao.insert(NoteConverter.toDatabase(note))
    }
}