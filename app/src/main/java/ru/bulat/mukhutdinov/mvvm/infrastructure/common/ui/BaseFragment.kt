package ru.bulat.mukhutdinov.mvvm.infrastructure.common.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.kcontext
import ru.bulat.mukhutdinov.mvvm.infrastructure.util.navigateTo
import ru.bulat.mukhutdinov.mvvm.infrastructure.util.navigateUp

abstract class BaseFragment<VM : BaseViewModel> : Fragment(), KodeinAware {

    override val kodein by closestKodein()

    override val kodeinContext = kcontext<Fragment>(this)

    val lifecycleOwner: LifecycleOwner = this

    fun navigateTo(id: Int, bundle: Bundle?) {
        view?.navigateTo(id, bundle)
    }

    fun navigateUp() {
        view?.navigateUp()
    }

    abstract val viewModel: VM
}