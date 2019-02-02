package ru.bulat.mukhutdinov.sample.main.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.bulat.mukhutdinov.sample.main.ui.MainAndroidViewModel

object MainInjectionModule {

    val module = module {
        viewModel { MainAndroidViewModel() }
    }
}