package ru.bulat.mukhutdinov.mvvm.usersList.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import org.kodein.di.generic.instance
import ru.bulat.mukhutdinov.mvvm.R
import ru.bulat.mukhutdinov.mvvm.databinding.UsersListBinding
import ru.bulat.mukhutdinov.mvvm.infrastructure.common.ui.BaseFragment
import ru.bulat.mukhutdinov.mvvm.user.ui.UserFragment.Companion.USER_ID_EXTRA

class UsersListFragment : BaseFragment<UsersListViewModel>() {

    override val viewModel: UsersListViewModel by instance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: UsersListBinding = DataBindingUtil.inflate(inflater, R.layout.users_list, container, false)

        binding.usersListViewModel = viewModel

        viewModel.onUserClicked.observe(this, Observer { user ->
            val bundle = bundleOf(
                USER_ID_EXTRA to user.id
            )
            navigateTo(R.id.action_usersListFragment_to_userFragment, bundle)
        })

        return binding.root
    }
}