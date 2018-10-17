package ru.bulat.mukhutdinov.mvvm.infrastructure.util

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation

fun View.navigateTo(id: Int, bundle: Bundle? = null) = Navigation.findNavController(this).navigate(id, bundle)
fun View.navigateUp() = Navigation.findNavController(this).navigateUp()