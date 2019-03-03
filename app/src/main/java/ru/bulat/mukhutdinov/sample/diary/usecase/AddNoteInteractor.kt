package ru.bulat.mukhutdinov.sample.diary.usecase

import ru.bulat.mukhutdinov.sample.diary.gateway.NoteGateway
import ru.bulat.mukhutdinov.sample.diary.model.Note
import java.util.Date

class AddNoteInteractor(private val noteGateway: NoteGateway) : AddNoteUseCase {

    override suspend fun execute(text: String) {
        val note = Note(0, text, Date().time)

        noteGateway.add(note)
    }
}