package ru.bulat.mukhutdinov.sample.postslist.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ru.bulat.mukhutdinov.sample.R
import ru.bulat.mukhutdinov.sample.databinding.PostsItemBinding
import ru.bulat.mukhutdinov.sample.post.model.Post
import ru.bulat.mukhutdinov.sample.postslist.ui.PostsListViewModel

class PostViewHolder(private val binding: PostsItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindTo(post: Post?, postsListViewModel: PostsListViewModel) {
        binding.post = post
        binding.postsListViewModel = postsListViewModel
        binding.icon.clipToOutline = true

        binding.executePendingBindings()
    }

    companion object {
        fun create(parent: ViewGroup): PostViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding: PostsItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.posts_item, parent, false)
            return PostViewHolder(binding)
        }
    }
}