package ru.bulat.mukhutdinov.mvvm.usersList.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton
import ru.bulat.mukhutdinov.mvvm.infrastructure.common.di.ViewModelFactory
import ru.bulat.mukhutdinov.mvvm.user.gateway.UserLocalGateway
import ru.bulat.mukhutdinov.mvvm.usersList.ui.UsersListAndroidViewModel
import ru.bulat.mukhutdinov.mvvm.usersList.ui.UsersListViewModel

object UsersListInjectionModule {

    val module = Kodein.Module(UsersListInjectionModule::class.java.name) {

        bind<UsersListViewModel>() with scoped(AndroidLifecycleScope<Fragment>()).singleton {
            val usersListViewModel = ViewModelProviders
                .of(context, UsersListViewModelFactory(instance()))
                .get(UsersListAndroidViewModel::class.java)

            usersListViewModel.lifecycleOwner = context.viewLifecycleOwner

            return@singleton usersListViewModel
        }
    }

    private class UsersListViewModelFactory(private val userLocalGateway: UserLocalGateway) : ViewModelFactory() {

        override fun viewModel(): ViewModel =
            UsersListAndroidViewModel(userLocalGateway)
    }
}