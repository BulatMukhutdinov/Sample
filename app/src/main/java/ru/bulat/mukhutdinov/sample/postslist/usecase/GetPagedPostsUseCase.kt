package ru.bulat.mukhutdinov.sample.postslist.usecase

import ru.bulat.mukhutdinov.sample.infrastructure.common.model.Listing
import ru.bulat.mukhutdinov.sample.post.model.Post
import ru.bulat.mukhutdinov.sample.infrastructure.common.usecase.Cancelable

interface GetPagedPostsUseCase : Cancelable {

    fun execute(): Listing<Post>
}