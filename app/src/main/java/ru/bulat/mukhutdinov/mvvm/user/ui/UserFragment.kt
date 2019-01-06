package ru.bulat.mukhutdinov.mvvm.user.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import org.kodein.di.generic.factory2
import ru.bulat.mukhutdinov.mvvm.MainActivity
import ru.bulat.mukhutdinov.mvvm.R
import ru.bulat.mukhutdinov.mvvm.databinding.UserBinding
import ru.bulat.mukhutdinov.mvvm.infrastructure.common.ui.BaseFragment
import ru.bulat.mukhutdinov.mvvm.infrastructure.exception.MvvmException
import ru.bulat.mukhutdinov.mvvm.infrastructure.util.Either
import ru.bulat.mukhutdinov.mvvm.infrastructure.util.toast

class UserFragment : BaseFragment<UserViewModel>() {

    private val userId by lazy { arguments?.let { UserFragmentArgs.fromBundle(it).userId } ?: "" }
    private val userViewModelFactory: (String, Fragment) -> UserViewModel by factory2()

    override val viewModel: UserViewModel by lazy { userViewModelFactory(userId, this) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: UserBinding = DataBindingUtil.inflate(inflater, R.layout.user, container, false)

        binding.userViewModel = viewModel

        viewModel.onSaveClicked.observe(
            viewLifecycleOwner,
            Observer<Either<Nothing, MvvmException>> { either ->
                either?.either(
                    completeCallback = { navigateUp() },
                    loadingCallback = { (activity as? MainActivity)?.setLoadingVisible(it) },
                    errorCallback = { context?.toast(R.string.common_exception) }
                )
            }
        )

        binding.icon.clipToOutline = true

        return binding.root
    }
}