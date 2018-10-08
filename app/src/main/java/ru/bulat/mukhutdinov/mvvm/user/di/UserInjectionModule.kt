package ru.bulat.mukhutdinov.mvvm.user.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.factory
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import ru.bulat.mukhutdinov.mvvm.common.db.MvvmDatabase
import ru.bulat.mukhutdinov.mvvm.common.di.ViewModelFactory
import ru.bulat.mukhutdinov.mvvm.user.db.UserDao
import ru.bulat.mukhutdinov.mvvm.user.gateway.UserLocalGateway
import ru.bulat.mukhutdinov.mvvm.user.gateway.UserRoomGateway
import ru.bulat.mukhutdinov.mvvm.user.ui.UserAndroidViewModel
import ru.bulat.mukhutdinov.mvvm.user.ui.contract.UserView
import ru.bulat.mukhutdinov.mvvm.user.ui.contract.UserViewModel

object UserInjectionModule {

    val module = Kodein.Module("UserInjectionModule") {

        bind<UserDao>() with singleton {
            return@singleton instance<MvvmDatabase>().userDao()
        }

        bind<UserLocalGateway>() with singleton {
            return@singleton UserRoomGateway(instance())
        }

        bind<UserViewModel>() with factory { userId: String, fragment: Fragment ->
            val userViewModel = ViewModelProviders
                .of(fragment, UserViewModelFactory(context as UserView, userId, instance()))
                .get(UserAndroidViewModel::class.java)

            userViewModel.view = context as UserView

            return@factory userViewModel
        }
    }

    private class UserViewModelFactory(private val view: UserView,
                                       private val userId: String,
                                       private val userLocalGateway: UserLocalGateway) : ViewModelFactory() {

        override fun viewModel(): ViewModel =
            UserAndroidViewModel(view, userId, userLocalGateway)
    }
}