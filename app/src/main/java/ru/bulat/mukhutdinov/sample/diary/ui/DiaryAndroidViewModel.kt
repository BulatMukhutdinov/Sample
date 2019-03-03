package ru.bulat.mukhutdinov.sample.diary.ui

import kotlinx.coroutines.Dispatchers
import ru.bulat.mukhutdinov.sample.diary.usecase.AddNoteUseCase
import ru.bulat.mukhutdinov.sample.diary.usecase.ObserveNotesUseCase
import ru.bulat.mukhutdinov.sample.infrastructure.common.ui.BaseCoroutineAndroidViewModel
import ru.bulat.mukhutdinov.sample.infrastructure.util.data.DataStateLiveData

class DiaryAndroidViewModel(
    private val addNoteUseCase: AddNoteUseCase,
    observeNotesUseCase: ObserveNotesUseCase
) : BaseCoroutineAndroidViewModel(), DiaryViewModel {

    override val notesWithDates: DataStateLiveData<List<Any>> = DataStateLiveData.create()

    init {
        notesWithDates.postToDataState(observeNotesUseCase::execute, Dispatchers.IO)
    }

    override fun addNote(text: String): DataStateLiveData<Unit> {
        val dataState = DataStateLiveData.createForSingle<Unit>()

        dataState.postToDataState({ addNoteUseCase.execute(text) }, Dispatchers.IO)

        return dataState
    }
}