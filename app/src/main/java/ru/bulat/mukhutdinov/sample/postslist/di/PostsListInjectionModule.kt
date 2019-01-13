package ru.bulat.mukhutdinov.sample.postslist.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.factory
import org.kodein.di.generic.instance
import org.kodein.di.generic.with
import ru.bulat.mukhutdinov.sample.infrastructure.common.ui.ViewModelFactory
import ru.bulat.mukhutdinov.sample.post.gateway.PostGateway
import ru.bulat.mukhutdinov.sample.postslist.ui.PostsListAndroidViewModel
import ru.bulat.mukhutdinov.sample.postslist.ui.PostsListViewModel

object PostsListInjectionModule {
    const val MY_BLOG_TAG = "my_blog_tag"

    val module = Kodein.Module(PostsListInjectionModule::class.java.name) {

        bind<PostsListViewModel>() with factory { fragment: Fragment, blogName: String ->
            val postsListViewModel = ViewModelProviders
                .of(fragment, PostsListViewModelFactory(instance(arg = blogName)))
                .get(PostsListAndroidViewModel::class.java)

            postsListViewModel.lifecycleOwner = fragment.viewLifecycleOwner

            return@factory postsListViewModel
        }

        constant(tag = MY_BLOG_TAG) with "biggybcool.tumblr.com"
    }

    private class PostsListViewModelFactory(private val postGateway: PostGateway) : ViewModelFactory() {

        override fun viewModel(): ViewModel =
            PostsListAndroidViewModel(postGateway)
    }
}