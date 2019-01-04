package ru.bulat.mukhutdinov.mvvm.usersList.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import org.kodein.di.generic.instance
import ru.bulat.mukhutdinov.mvvm.R
import ru.bulat.mukhutdinov.mvvm.databinding.UsersListBinding
import ru.bulat.mukhutdinov.mvvm.infrastructure.common.ui.BaseFragment

class UsersListFragment : BaseFragment<UsersListViewModel>() {

    override val viewModel: UsersListViewModel by instance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: UsersListBinding = DataBindingUtil.inflate(inflater, R.layout.users_list, container, false)

        binding.usersListViewModel = viewModel

        viewModel.onUserClicked.observe(viewLifecycleOwner, Observer { user ->
            val direction = UsersListFragmentDirections.usersListFragmentToUserFragment()
            direction.userId = user.id
            navigateTo(direction)
        })

        return binding.root
    }
}