package ru.bulat.mukhutdinov.sample.postslist.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.posts_create_picker.arrow
import kotlinx.android.synthetic.main.posts_create_picker.picker
import kotlinx.android.synthetic.main.posts_list.posts
import kotlinx.android.synthetic.main.posts_list.refresh
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.viewModel
import org.koin.core.parameter.parametersOf
import ru.bulat.mukhutdinov.sample.R
import ru.bulat.mukhutdinov.sample.infrastructure.common.model.NetworkState
import ru.bulat.mukhutdinov.sample.infrastructure.common.ui.BaseFragment
import ru.bulat.mukhutdinov.sample.postslist.di.PostsListInjectionModule
import ru.bulat.mukhutdinov.sample.postslist.ui.adapter.PostsAdapter
import timber.log.Timber

class PostsListFragment : BaseFragment<PostsListViewModel>() {

    override val viewModel
        by viewModel<PostsListAndroidViewModel> { parametersOf(myBlogName) }

    private val picasso: Picasso by inject()

    private val myBlogName: String by inject(PostsListInjectionModule.MY_BLOG_TAG)

    private lateinit var adapter: PostsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.posts_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRefresh()

        setupPosts()

        setupBottomSheetBehavior()
    }

    private fun setupRefresh() {
        refresh.setOnRefreshListener { viewModel.refresh() }

        viewModel.refreshState.observe(viewLifecycleOwner, Observer {
            refresh.isRefreshing = it == NetworkState.Loading
        })
    }

    private fun setupPosts() {
        adapter = PostsAdapter(picasso, viewModel) { index ->
            Timber.d("Item ${adapter.getItemAt(index)} was clicked")
        }

        posts.adapter = adapter
        posts.layoutManager = LinearLayoutManager(context)

        viewModel.posts.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        viewModel.networkState.observe(viewLifecycleOwner, Observer {
            viewModel.updateNetworkState(it)
            adapter.updateNetworkState(it)
        })
    }

    private fun setupBottomSheetBehavior() {
        val bottomSheetBehavior = BottomSheetBehavior.from(picker)

        bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            @SuppressLint("SwitchIntDef")
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        val stateSet = intArrayOf(android.R.attr.state_active)
                        arrow.setImageState(stateSet, true)
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        val stateSet = intArrayOf(android.R.attr.state_active * -1)
                        arrow.setImageState(stateSet, true)
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })
    }
}