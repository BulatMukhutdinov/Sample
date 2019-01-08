package ru.bulat.mukhutdinov.sample.postslist.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton
import ru.bulat.mukhutdinov.sample.infrastructure.common.di.ViewModelFactory
import ru.bulat.mukhutdinov.sample.post.gateway.PostGateway
import ru.bulat.mukhutdinov.sample.postslist.ui.PostsListAndroidViewModel

object PostsListInjectionModule {

    val module = Kodein.Module(PostsListInjectionModule::class.java.name) {

        bind<PostsListAndroidViewModel>() with scoped(AndroidLifecycleScope<Fragment>()).singleton {
            val postsListViewModel = ViewModelProviders
                .of(context, PostsListViewModelFactory(instance()))
                .get(PostsListAndroidViewModel::class.java)

            postsListViewModel.lifecycleOwner = context.viewLifecycleOwner

            return@singleton postsListViewModel
        }
    }

    private class PostsListViewModelFactory(private val postGateway: PostGateway) : ViewModelFactory() {

        override fun viewModel(): ViewModel =
            PostsListAndroidViewModel(postGateway)
    }
}