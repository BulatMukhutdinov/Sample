package ru.bulat.mukhutdinov.sample.auth.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.bulat.mukhutdinov.sample.auth.ui.authenticator.AuthenticatorAndroidViewModel
import ru.bulat.mukhutdinov.sample.auth.usecase.*

object AuthInjectionModule {

    val module = module {

        factory<HashPasswordUseCase> {
            HashPasswordInteractor()
        }

        factory<ValidateUsernameAndPasswordUseCase> {
            ValidateUsernameAndPasswordInteractor()
        }

        factory<AuthenticateUseCase> {
            AuthenticateInteractor(get())
        }

        viewModel {
            AuthenticatorAndroidViewModel(get(), get(), get())
        }
    }
}