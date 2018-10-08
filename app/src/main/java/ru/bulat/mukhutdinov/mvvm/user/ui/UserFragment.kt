package ru.bulat.mukhutdinov.mvvm.user.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import org.kodein.di.generic.factory2
import ru.bulat.mukhutdinov.mvvm.R
import ru.bulat.mukhutdinov.mvvm.common.ui.BaseFragment
import ru.bulat.mukhutdinov.mvvm.databinding.UserWrapperBinding
import ru.bulat.mukhutdinov.mvvm.user.ui.contract.UserView
import ru.bulat.mukhutdinov.mvvm.user.ui.contract.UserViewModel


class UserFragment : BaseFragment(), UserView {

    private val userViewModelFactory: (String, Fragment) -> UserViewModel by factory2()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: UserWrapperBinding = DataBindingUtil.inflate(inflater, R.layout.user_wrapper, container, false)

        val userId = arguments?.getString(UserAndroidViewModel.USER_ID_EXTRA).orEmpty()

        binding.userViewModel = userViewModelFactory(userId, this)

        return binding.root
    }
}