package ru.bulat.mukhutdinov.sample.post.model

import ru.bulat.mukhutdinov.sample.post.api.PostDto
import ru.bulat.mukhutdinov.sample.post.db.PostEntity

object PostConverter {

    fun toDatabase(source: Post) =
        PostEntity(
            id = source.id,
            avatar = source.avatar,
            login = source.login,
            url = source.url
        )

    fun fromDatabase(source: PostEntity) =
        Post(
            id = source.id,
            avatar = source.avatar,
            login = source.login,
            url = source.url
        )

    fun fromNetwork(source: PostDto) =
        PostEntity(
            id = source.id,
            avatar = source.avatar,
            login = source.login,
            url = source.url
        )

    fun toDatabase(source: List<PostDto>) =
        mutableListOf<PostEntity>().also {
            source.forEach { post -> it.add(fromNetwork(post)) }
        } as List<PostEntity>
}