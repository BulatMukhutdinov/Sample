package ru.bulat.mukhutdinov.sample.post.gateway

import io.reactivex.Completable
import io.reactivex.Maybe
import ru.bulat.mukhutdinov.sample.infrastructure.common.model.Listing
import ru.bulat.mukhutdinov.sample.post.model.Post

interface PostGateway {

    fun getPaged(): Listing<Post>

    fun findById(id: String): Maybe<Post>

    fun update(post: Post): Completable

    fun clearSubscriptions()
}