package ru.bulat.mukhutdinov.sample.main.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton
import ru.bulat.mukhutdinov.sample.infrastructure.common.di.ViewModelFactory
import ru.bulat.mukhutdinov.sample.main.ui.MainAndroidViewModel
import ru.bulat.mukhutdinov.sample.main.ui.MainViewModel

object MainInjectionModule {

    val module = Kodein.Module(MainInjectionModule::class.java.name) {

        bind<MainViewModel>() with scoped(AndroidLifecycleScope<Fragment>()).singleton {
            return@singleton ViewModelProviders
                .of(context, MainViewModelFactory())
                .get(MainAndroidViewModel::class.java)
        }
    }

    private class MainViewModelFactory : ViewModelFactory() {

        override fun viewModel(): ViewModel =
            MainAndroidViewModel()
    }
}