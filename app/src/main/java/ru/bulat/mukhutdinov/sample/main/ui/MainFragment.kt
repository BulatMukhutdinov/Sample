package ru.bulat.mukhutdinov.sample.main.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.viewModel
import ru.bulat.mukhutdinov.sample.R
import ru.bulat.mukhutdinov.sample.databinding.MainBinding
import ru.bulat.mukhutdinov.sample.infrastructure.common.ui.BaseFragment
import ru.bulat.mukhutdinov.sample.infrastructure.extension.toast

class MainFragment : BaseFragment<MainViewModel>() {

    override val viewModel by viewModel<MainAndroidViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: MainBinding = DataBindingUtil.inflate(inflater, R.layout.main, container, false)

        binding.usersListIcon.clipToOutline = true
        binding.blogIcon.clipToOutline = true

        binding.mainViewModel = viewModel

        listenForClicks()

        return binding.root
    }

    private fun listenForClicks() {
        viewModel.onUsersListAppClicked.observe(viewLifecycleOwner,
            Observer { either ->
                either?.either(
                    completeCallback = {
                        val direction = MainFragmentDirections.mainFragmentToUsersListFragment()
                        navigateTo(direction)
                    },
                    errorCallback = { context?.toast(R.string.common_exception) }
                )
            })

        viewModel.onPostsListAppClicked.observe(viewLifecycleOwner,
            Observer { either ->
                either?.either(
                    completeCallback = {
                        val direction = MainFragmentDirections.mainFragmentToPostsListFragment()
                        navigateTo(direction)
                    },
                    errorCallback = { context?.toast(R.string.common_exception) }
                )
            })
    }
}