package ru.bulat.mukhutdinov.sample.postslist.ui.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import ru.bulat.mukhutdinov.sample.R
import ru.bulat.mukhutdinov.sample.databinding.PostsErrorItemBinding
import ru.bulat.mukhutdinov.sample.infrastructure.common.ui.BaseViewHolder
import ru.bulat.mukhutdinov.sample.post.model.Post
import ru.bulat.mukhutdinov.sample.postslist.ui.PostsListViewModel

class PostNetworkErrorViewHolder(private val binding: PostsErrorItemBinding) :
    BaseViewHolder<Post>(binding.root) {

    fun bindTo(postsListViewModel: PostsListViewModel) {
        binding.postsListViewModel = postsListViewModel

        binding.executePendingBindings()
    }

    companion object {
        fun create(parent: ViewGroup): PostNetworkErrorViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding: PostsErrorItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.posts_error_item, parent, false)
            return PostNetworkErrorViewHolder(binding)
        }
    }
}