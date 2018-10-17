package ru.bulat.mukhutdinov.mvvm.user.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import org.kodein.di.generic.factory2
import ru.bulat.mukhutdinov.mvvm.R
import ru.bulat.mukhutdinov.mvvm.databinding.UserWrapperBinding
import ru.bulat.mukhutdinov.mvvm.infrastructure.common.ui.BaseFragment


class UserFragment : BaseFragment<UserViewModel>() {

    private val userId by lazy { arguments?.getString(USER_ID_EXTRA).orEmpty() }
    private val userViewModelFactory: (String, Fragment) -> UserViewModel by factory2()

    override val viewModel: UserViewModel by lazy { userViewModelFactory(userId, this) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: UserWrapperBinding = DataBindingUtil.inflate(inflater, R.layout.user_wrapper, container, false)

        binding.userViewModel = viewModel

        viewModel.onSaveClicked.observe(this, Observer { navigateUp() })

        return binding.root
    }

    companion object {
        const val USER_ID_EXTRA = "USER_ID_EXTRA"
    }
}