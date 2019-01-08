package ru.bulat.mukhutdinov.sample.user.model

import ru.bulat.mukhutdinov.sample.infrastructure.common.model.BaseModel

data class User(override val id: Int,
                var name: String,
                var icon: String,
                var iconThumbnail: String,
                var description: String) : BaseModel(id)