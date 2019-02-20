package ru.bulat.mukhutdinov.sample.post.gateway

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import ru.bulat.mukhutdinov.sample.infrastructure.common.model.Listing
import ru.bulat.mukhutdinov.sample.post.model.Post
import ru.bulat.mukhutdinov.sample.post.model.TextPost

interface PostGateway {

    val currentBlogName: String

    fun getPaged(): Listing<Post>

    fun findById(id: Long): Maybe<Post>

    fun updateId(oldId: Long, newId: Long): Completable

    fun delete(post: TextPost): Completable

    fun createTextPostLocal(post: TextPost): Completable

    fun createTextPostRemote(post: TextPost): Completable

    fun currentAvatar(): Single<String>

    fun clearSubscriptions()
}