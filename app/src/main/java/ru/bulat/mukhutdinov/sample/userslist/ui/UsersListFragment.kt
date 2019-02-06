package ru.bulat.mukhutdinov.sample.userslist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import org.koin.androidx.viewmodel.ext.viewModel
import org.koin.core.parameter.parametersOf
import ru.bulat.mukhutdinov.sample.R
import ru.bulat.mukhutdinov.sample.databinding.UsersListBinding
import ru.bulat.mukhutdinov.sample.infrastructure.common.ui.BaseFragment
import ru.bulat.mukhutdinov.sample.infrastructure.extension.observeViewState

class UsersListFragment : BaseFragment<UsersListViewModel>() {

    override val viewModel by viewModel<UsersListAndroidViewModel> { parametersOf(this) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: UsersListBinding = DataBindingUtil.inflate(inflater, R.layout.users_list, container, false)

        binding.usersListViewModel = viewModel

        viewModel.onUserClicked.observeViewState(
            owner = viewLifecycleOwner,
            dataCallback = { user ->
                val direction = UsersListFragmentDirections.usersListFragmentToUserFragment()
                direction.userId = user.id
                navigateTo(direction)
            })

        return binding.root
    }
}