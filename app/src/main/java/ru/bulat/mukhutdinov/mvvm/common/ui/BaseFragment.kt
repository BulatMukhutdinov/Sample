package ru.bulat.mukhutdinov.mvvm.common.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.kcontext
import ru.bulat.mukhutdinov.mvvm.util.navigateTo
import ru.bulat.mukhutdinov.mvvm.util.navigateUp

abstract class BaseFragment : Fragment(), BaseView {

    override val kodein by closestKodein()

    override val kodeinContext = kcontext<Fragment>(this)

    override val lifecycleOwner: LifecycleOwner = this

    override fun navigateTo(id: Int, bundle: Bundle?) {
        view?.navigateTo(id, bundle)
    }

    override fun navigateUp() {
        view?.navigateUp()
    }
}