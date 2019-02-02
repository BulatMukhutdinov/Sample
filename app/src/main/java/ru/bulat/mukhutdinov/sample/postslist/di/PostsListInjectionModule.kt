package ru.bulat.mukhutdinov.sample.postslist.di

import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import ru.bulat.mukhutdinov.sample.postslist.ui.PostsListAndroidViewModel

object PostsListInjectionModule {
    const val MY_BLOG_TAG = "my_blog_tag"

    val module = module {

        viewModel { (fragment: Fragment, blogName: String) ->
            val viewModel = PostsListAndroidViewModel(get { parametersOf(blogName) })
            viewModel.lifecycleOwner = fragment.viewLifecycleOwner
            return@viewModel viewModel
        }

        single(MY_BLOG_TAG) { "biggybcool.tumblr.com" }
    }
}