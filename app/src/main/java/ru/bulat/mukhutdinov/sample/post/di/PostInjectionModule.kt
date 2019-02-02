package ru.bulat.mukhutdinov.sample.post.di

import org.koin.dsl.module
import ru.bulat.mukhutdinov.sample.infrastructure.common.db.SampleDatabase
import ru.bulat.mukhutdinov.sample.post.gateway.PostBoundaryGateway
import ru.bulat.mukhutdinov.sample.post.gateway.PostGateway

object PostInjectionModule {

    val module = module {
        single { get<SampleDatabase>().postDao() }

        // todo use multiton
        single<PostGateway> { (blogName: String) ->
            PostBoundaryGateway(
                jumblr = get(),
                db = get(),
                blogName = blogName,
                networkPageSize = 20,
                postDao = get()
            )
        }
    }
}