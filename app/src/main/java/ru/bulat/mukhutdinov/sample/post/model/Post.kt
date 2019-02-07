package ru.bulat.mukhutdinov.sample.post.model

import ru.bulat.mukhutdinov.sample.infrastructure.common.model.BaseModel

open class Post(
    override val id: Long,
    open var avatar: String,
    open var title: String,
    open var blogName: String
) : BaseModel(id)