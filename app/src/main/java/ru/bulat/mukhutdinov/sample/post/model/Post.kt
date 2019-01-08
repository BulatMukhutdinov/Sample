package ru.bulat.mukhutdinov.sample.post.model

import ru.bulat.mukhutdinov.sample.infrastructure.common.model.BaseModel

data class Post(
    override val id: Int,
    var login: String,
    var avatar: String,
    var url: String
) : BaseModel(id)