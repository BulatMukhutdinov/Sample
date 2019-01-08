package ru.bulat.mukhutdinov.sample.post.di

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import ru.bulat.mukhutdinov.sample.infrastructure.common.api.SampleApi
import ru.bulat.mukhutdinov.sample.infrastructure.common.db.SampleDatabase
import ru.bulat.mukhutdinov.sample.post.db.PostDao
import ru.bulat.mukhutdinov.sample.post.gateway.PostBoundaryGateway
import ru.bulat.mukhutdinov.sample.post.gateway.PostGateway
import java.util.concurrent.Executors

object PostInjectionModule {

    val module = Kodein.Module(PostInjectionModule::class.java.name) {
        bind<PostDao>() with singleton {
            return@singleton instance<SampleDatabase>().postDao()
        }

        bind<PostGateway>() with singleton {
            return@singleton PostBoundaryGateway(
                api = instance<Retrofit>().create(SampleApi::class.java),
                db = instance(),
                executor = Executors.newSingleThreadExecutor(),
                networkPageSize = 30,
                postDao = instance()
            )
        }
    }
}