package ru.bulat.mukhutdinov.sample.diary.model

import ru.bulat.mukhutdinov.sample.infrastructure.common.model.BaseModel

data class Note(override val id: Long,
                val text: String,
                val timestamp: Long) : BaseModel(id) {

    val date: String = "$timestamp"
}