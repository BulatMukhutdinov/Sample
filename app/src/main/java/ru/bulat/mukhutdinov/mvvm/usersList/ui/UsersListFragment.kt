package ru.bulat.mukhutdinov.mvvm.usersList.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import org.kodein.di.generic.instance
import ru.bulat.mukhutdinov.mvvm.R
import ru.bulat.mukhutdinov.mvvm.common.ui.BaseFragment
import ru.bulat.mukhutdinov.mvvm.databinding.UsersListBinding
import ru.bulat.mukhutdinov.mvvm.usersList.ui.border.UsersListView
import ru.bulat.mukhutdinov.mvvm.usersList.ui.border.UsersListViewModel

class UsersListFragment : BaseFragment(), UsersListView {

    private val usersListViewModel: UsersListViewModel by instance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: UsersListBinding = DataBindingUtil.inflate(inflater, R.layout.users_list, container, false)

        binding.usersListViewModel = usersListViewModel

        return binding.root
    }
}