package ru.bulat.mukhutdinov.sample.postslist.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.posts_create_picker.arrow
import kotlinx.android.synthetic.main.posts_create_picker.navigationView
import kotlinx.android.synthetic.main.posts_create_picker.picker
import kotlinx.android.synthetic.main.posts_list.posts
import kotlinx.android.synthetic.main.posts_list.refresh
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.viewModel
import org.koin.core.parameter.parametersOf
import ru.bulat.mukhutdinov.sample.R
import ru.bulat.mukhutdinov.sample.infrastructure.common.model.NetworkState
import ru.bulat.mukhutdinov.sample.infrastructure.common.ui.BaseFragment
import ru.bulat.mukhutdinov.sample.post.model.PostType
import ru.bulat.mukhutdinov.sample.post.ui.PostCreateActivity
import ru.bulat.mukhutdinov.sample.post.ui.PostCreateActivity.Companion.POST_TYPE
import ru.bulat.mukhutdinov.sample.postslist.di.PostsListInjectionModule
import ru.bulat.mukhutdinov.sample.postslist.ui.adapter.PostsAdapter
import timber.log.Timber

class PostsListFragment : BaseFragment<PostsListViewModel>(), NavigationView.OnNavigationItemSelectedListener {

    override val viewModel
        by viewModel<PostsListAndroidViewModel> { parametersOf(myBlogName, PAGE_SIZE) }

    private val picasso: Picasso by inject()

    private val myBlogName: String by inject(PostsListInjectionModule.MY_BLOG_TAG)

    private lateinit var adapter: PostsAdapter

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.posts_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigationView.setNavigationItemSelectedListener(this)

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
        bottomSheetBehavior = BottomSheetBehavior.from(picker)

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_POST_CREATE) {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        if (resultCode == Activity.RESULT_CANCELED) {
            Timber.w("Failed to create new post")
            // todo show some error message to user
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_text -> startPostCreateActivity(PostType.TEXT)
            R.id.menu_audio -> startPostCreateActivity(PostType.ANSWER)
            R.id.menu_image -> startPostCreateActivity(PostType.IMAGE)
            R.id.menu_link -> startPostCreateActivity(PostType.LINK)
            R.id.menu_quote -> startPostCreateActivity(PostType.QUOTE)
            R.id.menu_video -> startPostCreateActivity(PostType.VIDEO)
            else -> Timber.e("Unknown create post picker item is clicked")
        }
        return true
    }

    private fun startPostCreateActivity(type: PostType) {
        val intent = Intent(context, PostCreateActivity::class.java)
        intent.putExtra(POST_TYPE, type)
        startActivityForResult(intent, REQUEST_POST_CREATE)
    }

    companion object {
        private const val REQUEST_POST_CREATE = 101
        private const val PAGE_SIZE = 10
    }
}