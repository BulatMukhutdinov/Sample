package ru.bulat.mukhutdinov.sample.postslist.ui.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.bulat.mukhutdinov.sample.infrastructure.util.DiffUtilItemCallback
import ru.bulat.mukhutdinov.sample.post.model.Post
import ru.bulat.mukhutdinov.sample.postslist.ui.PostsListViewModel

class PostsAdapter(private val postsListViewModel: PostsListViewModel)
    : PagedListAdapter<Post, RecyclerView.ViewHolder>(DiffUtilItemCallback<Post>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        PostViewHolder.create(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PostViewHolder).bindTo(getItem(position), postsListViewModel)
    }
}