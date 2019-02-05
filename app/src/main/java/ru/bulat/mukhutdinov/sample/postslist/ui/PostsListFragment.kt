package ru.bulat.mukhutdinov.sample.postslist.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.viewModel
import org.koin.core.parameter.parametersOf
import ru.bulat.mukhutdinov.sample.R
import ru.bulat.mukhutdinov.sample.databinding.PostsListBinding
import ru.bulat.mukhutdinov.sample.infrastructure.common.ui.BaseFragment
import ru.bulat.mukhutdinov.sample.postslist.di.PostsListInjectionModule

class PostsListFragment : BaseFragment<PostsListViewModel>() {

    override val viewModel
        by viewModel<PostsListAndroidViewModel> { parametersOf(this, myBlogName) }

    private val myBlogName: String by inject(PostsListInjectionModule.MY_BLOG_TAG)

    private lateinit var binding: PostsListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.posts_list, container, false)

        binding.postsListViewModel = viewModel

        setupBottomSheetBehavior()

        return binding.root
    }

    private fun setupBottomSheetBehavior() {
        val bottomSheetBehavior = BottomSheetBehavior.from(binding.picker.picker)

        bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            @SuppressLint("SwitchIntDef")
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        val stateSet = intArrayOf(android.R.attr.state_active)
                        binding.picker.arrow.setImageState(stateSet, true)
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        val stateSet = intArrayOf(android.R.attr.state_active * -1)
                        binding.picker.arrow.setImageState(stateSet, true)
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })
    }
}