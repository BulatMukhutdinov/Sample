package ru.bulat.mukhutdinov.sample.diary.model

import ru.bulat.mukhutdinov.sample.diary.db.NoteEntity
import java.util.stream.Collectors

object NoteConverter {

    fun toDatabase(source: Note): NoteEntity =
        NoteEntity(id = source.id, text = source.text, timestamp = source.timestamp)

    fun fromDatabase(source: NoteEntity): Note =
        Note(id = source.id, text = source.text, timestamp = source.timestamp)

    fun fromDatabase(source: List<NoteEntity>): List<Note> =
        source.stream()
            .map { NoteConverter.fromDatabase(it) }
            .collect(Collectors.toList())
}