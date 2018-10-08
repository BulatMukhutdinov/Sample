package ru.bulat.mukhutdinov.mvvm.common.ui

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import org.kodein.di.KodeinAware

interface BaseView : KodeinAware {

    fun navigateTo(id: Int, bundle: Bundle? = null)

    fun navigateUp()

    val lifecycleOwner: LifecycleOwner
}