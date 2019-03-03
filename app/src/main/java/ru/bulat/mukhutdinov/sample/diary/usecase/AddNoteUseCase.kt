package ru.bulat.mukhutdinov.sample.diary.usecase

interface AddNoteUseCase {

    suspend fun execute(text: String)
}