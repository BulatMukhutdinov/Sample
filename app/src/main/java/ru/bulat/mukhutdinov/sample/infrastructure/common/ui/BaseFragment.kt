package ru.bulat.mukhutdinov.sample.infrastructure.common.ui

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.kcontext

abstract class BaseFragment<VM : BaseViewModel> : Fragment(), KodeinAware {

    override val kodein by closestKodein()

    override val kodeinContext = kcontext<Fragment>(this)

    val lifecycleOwner: LifecycleOwner = this

    fun navigateTo(action: NavDirections) {
        view?.findNavController()?.navigate(action)
    }

    fun navigateUp() {
        view?.findNavController()?.navigateUp()
    }

    abstract val viewModel: VM
}