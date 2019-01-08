package ru.bulat.mukhutdinov.sample.infrastructure.common.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

abstract class ViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = viewModel()
        return when (modelClass) {
            viewModel::class.java -> viewModel as T
            else -> throw RuntimeException("Failed to create ViewModel of class $modelClass. " +
                "Actual class is ${viewModel::class.java} ")
        }
    }

    abstract fun viewModel(): ViewModel
}