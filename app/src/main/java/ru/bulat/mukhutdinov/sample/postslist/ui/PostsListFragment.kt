package ru.bulat.mukhutdinov.sample.postslist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import org.kodein.di.generic.instance
import ru.bulat.mukhutdinov.sample.MainActivity
import ru.bulat.mukhutdinov.sample.R
import ru.bulat.mukhutdinov.sample.databinding.PostsListBinding
import ru.bulat.mukhutdinov.sample.infrastructure.common.model.NetworkState
import ru.bulat.mukhutdinov.sample.infrastructure.common.ui.BaseFragment

class PostsListFragment : BaseFragment<PostsListViewModel>() {

    override val viewModel: PostsListViewModel by instance()

    private lateinit var binding: PostsListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.posts_list, container, false)

        binding.postsListViewModel = viewModel

        viewModel.networkState.observe(this, Observer { networkState ->
            (activity as MainActivity).setLoadingVisible(networkState == NetworkState.Loading)
        })

        return binding.root
    }
}