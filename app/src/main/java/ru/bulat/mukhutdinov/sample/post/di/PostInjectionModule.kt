package ru.bulat.mukhutdinov.sample.post.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.bulat.mukhutdinov.sample.infrastructure.common.db.SampleDatabase
import ru.bulat.mukhutdinov.sample.post.gateway.PostBoundaryGateway
import ru.bulat.mukhutdinov.sample.post.gateway.PostGateway
import ru.bulat.mukhutdinov.sample.post.ui.textcreate.TextPostCreateAndroidViewModel
import ru.bulat.mukhutdinov.sample.post.usecase.CreateTextPostInteractor
import ru.bulat.mukhutdinov.sample.post.usecase.CreateTextPostUseCase
import ru.bulat.mukhutdinov.sample.post.usecase.ValidateTextPostInteractor
import ru.bulat.mukhutdinov.sample.post.usecase.ValidateTextPostUseCase

object PostInjectionModule {

    val module = module {
        single { get<SampleDatabase>().postDao() }

        single<PostGateway> { (blogName: String, pageSize: Int) ->
            PostBoundaryGateway(
                    jumblr = get(),
                    db = get(),
                    blogName = blogName,
                    networkPageSize = pageSize,
                    postDao = get()
            )
        }

        viewModel {
            return@viewModel TextPostCreateAndroidViewModel(get(), get())
        }

        factory<ValidateTextPostUseCase> {
            ValidateTextPostInteractor()
        }

        factory<CreateTextPostUseCase> {
            CreateTextPostInteractor(get())
        }
    }
}