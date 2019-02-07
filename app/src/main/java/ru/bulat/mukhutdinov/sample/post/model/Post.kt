package ru.bulat.mukhutdinov.sample.post.model

import ru.bulat.mukhutdinov.sample.infrastructure.common.model.BaseModel

open class Post(
    override val id: Long,
    open val avatar: String,
    open val date: String,
    open val tags: List<String>,
    open val blogName: String,
    open var isLiked: Boolean
) : BaseModel(id)