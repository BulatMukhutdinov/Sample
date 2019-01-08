package ru.bulat.mukhutdinov.sample.userslist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import org.kodein.di.generic.instance
import ru.bulat.mukhutdinov.sample.R
import ru.bulat.mukhutdinov.sample.databinding.UsersListBinding
import ru.bulat.mukhutdinov.sample.infrastructure.common.ui.BaseFragment
import ru.bulat.mukhutdinov.sample.infrastructure.exception.SampleException
import ru.bulat.mukhutdinov.sample.infrastructure.util.Either
import ru.bulat.mukhutdinov.sample.infrastructure.util.toast
import ru.bulat.mukhutdinov.sample.user.model.User

class UsersListFragment : BaseFragment<UsersListViewModel>() {

    override val viewModel: UsersListViewModel by instance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: UsersListBinding = DataBindingUtil.inflate(inflater, R.layout.users_list, container, false)

        binding.usersListViewModel = viewModel

        viewModel.onUserClicked.observe(viewLifecycleOwner,
            Observer<Either<User, SampleException>> { either ->
                either?.either(
                    dataCallback = { user ->
                        val direction = UsersListFragmentDirections.usersListFragmentToUserFragment()
                        direction.userId = user.id
                        navigateTo(direction)
                    },
                    errorCallback = { context?.toast(R.string.common_exception) }
                )
            })

        return binding.root
    }
}