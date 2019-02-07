package ru.bulat.mukhutdinov.sample.user.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.bulat.mukhutdinov.sample.infrastructure.common.db.SampleDatabase
import ru.bulat.mukhutdinov.sample.user.gateway.UserDbGateway
import ru.bulat.mukhutdinov.sample.user.gateway.UserGateway
import ru.bulat.mukhutdinov.sample.user.ui.UserAndroidViewModel

object UserInjectionModule {

    val module = module {

        single { get<SampleDatabase>().userDao() }

        single<UserGateway> { UserDbGateway(get()) }

        viewModel { (userId: Long) -> UserAndroidViewModel(userId, get()) }
    }
}