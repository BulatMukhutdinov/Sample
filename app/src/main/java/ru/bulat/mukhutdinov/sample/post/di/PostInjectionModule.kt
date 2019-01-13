package ru.bulat.mukhutdinov.sample.post.di

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.multiton
import org.kodein.di.generic.singleton
import ru.bulat.mukhutdinov.sample.infrastructure.common.db.SampleDatabase
import ru.bulat.mukhutdinov.sample.post.db.PostDao
import ru.bulat.mukhutdinov.sample.post.gateway.PostBoundaryGateway
import ru.bulat.mukhutdinov.sample.post.gateway.PostGateway

object PostInjectionModule {

    val module = Kodein.Module(PostInjectionModule::class.java.name) {
        bind<PostDao>() with singleton {
            return@singleton instance<SampleDatabase>().postDao()
        }

        bind<PostGateway>() with multiton { blogName: String ->
            return@multiton PostBoundaryGateway(
                jumblr = instance(),
                db = instance(),
                blogName = blogName,
                networkPageSize = 10,
                postDao = instance()
            )
        }
    }
}