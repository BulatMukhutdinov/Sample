package ru.bulat.mukhutdinov.mvvm.user.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.factory
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import ru.bulat.mukhutdinov.mvvm.infrastructure.common.db.MvvmDatabase
import ru.bulat.mukhutdinov.mvvm.infrastructure.common.di.ViewModelFactory
import ru.bulat.mukhutdinov.mvvm.user.db.UserDao
import ru.bulat.mukhutdinov.mvvm.user.gateway.UserLocalGateway
import ru.bulat.mukhutdinov.mvvm.user.gateway.UserDbGateway
import ru.bulat.mukhutdinov.mvvm.user.ui.UserAndroidViewModel
import ru.bulat.mukhutdinov.mvvm.user.ui.UserViewModel

object UserInjectionModule {

    val module = Kodein.Module(UserInjectionModule::class.java.name) {

        bind<UserDao>() with singleton {
            return@singleton instance<MvvmDatabase>().userDao()
        }

        bind<UserLocalGateway>() with singleton {
            return@singleton UserDbGateway(instance())
        }

        bind<UserViewModel>() with factory { userId: String, fragment: Fragment ->
            return@factory ViewModelProviders
                .of(fragment, UserViewModelFactory(userId, instance()))
                .get(UserAndroidViewModel::class.java)
        }
    }

    private class UserViewModelFactory(private val userId: String,
                                       private val userLocalGateway: UserLocalGateway) : ViewModelFactory() {

        override fun viewModel(): ViewModel =
            UserAndroidViewModel(userId, userLocalGateway)
    }
}