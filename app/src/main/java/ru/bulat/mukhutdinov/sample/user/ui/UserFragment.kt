package ru.bulat.mukhutdinov.sample.user.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.viewModel
import org.koin.core.parameter.parametersOf
import ru.bulat.mukhutdinov.sample.R
import ru.bulat.mukhutdinov.sample.databinding.UserBinding
import ru.bulat.mukhutdinov.sample.infrastructure.common.ui.BaseFragment
import ru.bulat.mukhutdinov.sample.infrastructure.extension.toast

class UserFragment : BaseFragment<UserViewModel>() {

    private val userId by lazy { arguments?.let { UserFragmentArgs.fromBundle(it).userId } ?: 0 }

    override val viewModel by viewModel<UserAndroidViewModel> { parametersOf(userId) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: UserBinding = DataBindingUtil.inflate(inflater, R.layout.user, container, false)

        binding.userViewModel = viewModel

        viewModel.onSaveClicked.observe(
            viewLifecycleOwner,
            Observer { either ->
                either?.either(
                    completeCallback = { navigateUp() },
                    errorCallback = { context?.toast(R.string.common_exception) }
                )
            }
        )

        binding.icon.clipToOutline = true

        return binding.root
    }
}