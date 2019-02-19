package ru.bulat.mukhutdinov.sample.main.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.main.*
import org.koin.androidx.viewmodel.ext.viewModel
import ru.bulat.mukhutdinov.sample.R
import ru.bulat.mukhutdinov.sample.infrastructure.common.ui.BaseFragment

class MainFragment : BaseFragment<MainViewModel>() {

    override val viewModel by viewModel<MainAndroidViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.main, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        usersListIcon.clipToOutline = true
        blogIcon.clipToOutline = true
        customAccountIcon.clipToOutline = true

        listenForClicks()
    }

    private fun listenForClicks() {
        usersListApp.setOnClickListener {
            val direction = MainFragmentDirections.mainFragmentToUsersListFragment()
            navigateTo(direction)
        }

        blogApp.setOnClickListener {
            val direction = MainFragmentDirections.mainFragmentToPostsListFragment()
            navigateTo(direction)
        }

        customAccountApp.setOnClickListener {
            val direction = MainFragmentDirections.mainFragmentToAuthFragment()
            navigateTo(direction)
        }
    }
}