package ru.bulat.mukhutdinov.sample.diary.ui

import ru.bulat.mukhutdinov.sample.infrastructure.common.ui.BaseViewModel
import ru.bulat.mukhutdinov.sample.infrastructure.util.data.DataStateLiveData

interface DiaryViewModel : BaseViewModel {

    val notesWithDates: DataStateLiveData<List<Any>>

    fun addNote(text: String): DataStateLiveData<Unit>
}