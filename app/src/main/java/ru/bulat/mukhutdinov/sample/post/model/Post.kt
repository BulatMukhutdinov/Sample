package ru.bulat.mukhutdinov.sample.post.model

import ru.bulat.mukhutdinov.sample.infrastructure.common.model.BaseModel

data class Post(
    override val id: Long,
    var body: String,
    var title: String,
    var date: String,
    var tags: List<String>
) : BaseModel(id)