package ru.bulat.mukhutdinov.sample.postslist.ui

import androidx.databinding.BindingAdapter
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.bulat.mukhutdinov.sample.R
import ru.bulat.mukhutdinov.sample.infrastructure.exception.SampleException
import ru.bulat.mukhutdinov.sample.infrastructure.util.Either
import ru.bulat.mukhutdinov.sample.infrastructure.util.toast
import ru.bulat.mukhutdinov.sample.post.model.Post
import ru.bulat.mukhutdinov.sample.postslist.ui.adapter.PostsAdapter

@BindingAdapter("app:postsListViewModel")
fun bind(recyclerView: RecyclerView, postsListViewModel: PostsListViewModel) {
    val adapter = PostsAdapter(postsListViewModel)

    recyclerView.adapter = adapter
    recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)

    postsListViewModel.posts.observe(
        postsListViewModel.lifecycleOwner,
        Observer<Either<PagedList<Post>, SampleException>> { either ->
            either?.either(
                dataCallback = { adapter.submitList(it) },
                errorCallback = { recyclerView.context.toast(R.string.common_exception) }
            )
        }
    )
}