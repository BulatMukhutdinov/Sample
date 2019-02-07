package ru.bulat.mukhutdinov.sample.postslist.usecase

import ru.bulat.mukhutdinov.sample.infrastructure.common.model.Listing
import ru.bulat.mukhutdinov.sample.postslist.usecase.GetPagedPostsUseCase
import ru.bulat.mukhutdinov.sample.post.gateway.PostGateway
import ru.bulat.mukhutdinov.sample.post.model.Post

class GetPagedPostsInteractor(private val postGateway: PostGateway) : GetPagedPostsUseCase {

    override fun execute(): Listing<Post> =
        postGateway.getPaged()

    override fun cancel() =
        postGateway.clearSubscriptions()
}