package ru.bulat.mukhutdinov.sample.infrastructure.common.ui

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavDirections
import androidx.navigation.findNavController

abstract class BaseFragment<VM : BaseViewModel> : Fragment() {

    val lifecycleOwner: LifecycleOwner = this

    fun navigateTo(action: NavDirections) {
        view?.findNavController()?.navigate(action)
    }

    fun navigateUp() {
        view?.findNavController()?.navigateUp()
    }

    abstract val viewModel: VM
}