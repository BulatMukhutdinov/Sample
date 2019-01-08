package ru.bulat.mukhutdinov.sample.user.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import org.kodein.di.generic.factory2
import ru.bulat.mukhutdinov.sample.R
import ru.bulat.mukhutdinov.sample.databinding.UserBinding
import ru.bulat.mukhutdinov.sample.infrastructure.common.ui.BaseFragment
import ru.bulat.mukhutdinov.sample.infrastructure.exception.SampleException
import ru.bulat.mukhutdinov.sample.infrastructure.util.Either
import ru.bulat.mukhutdinov.sample.infrastructure.util.toast

class UserFragment : BaseFragment<UserViewModel>() {

    private val userId by lazy { arguments?.let { UserFragmentArgs.fromBundle(it).userId } ?: 0 }
    private val userViewModelFactory: (Int, Fragment) -> UserViewModel by factory2()

    override val viewModel: UserViewModel by lazy { userViewModelFactory(userId, this) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: UserBinding = DataBindingUtil.inflate(inflater, R.layout.user, container, false)

        binding.userViewModel = viewModel

        viewModel.onSaveClicked.observe(
            viewLifecycleOwner,
            Observer<Either<Unit, SampleException>> { either ->
                either?.either(
                    completeCallback = { navigateUp() },
                    loadingCallback = { (activity as? ru.bulat.mukhutdinov.sample.MainActivity)?.setLoadingVisible(it) },
                    errorCallback = { context?.toast(R.string.common_exception) }
                )
            }
        )

        binding.icon.clipToOutline = true

        return binding.root
    }
}