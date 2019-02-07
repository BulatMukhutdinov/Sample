package ru.bulat.mukhutdinov.sample.userslist.di

import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.bulat.mukhutdinov.sample.userslist.interactor.GetAllUsersInteractor
import ru.bulat.mukhutdinov.sample.userslist.usecase.GetAllUsersUseCase
import ru.bulat.mukhutdinov.sample.userslist.ui.UsersListAndroidViewModel

object UsersListInjectionModule {

    val module = module {

        viewModel { (fragment: Fragment) ->
            val viewM = UsersListAndroidViewModel(get())

            viewM.lifecycleOwner = fragment.viewLifecycleOwner

            return@viewModel viewM
        }

        factory<GetAllUsersUseCase> {
            GetAllUsersInteractor(get())
        }
    }
}