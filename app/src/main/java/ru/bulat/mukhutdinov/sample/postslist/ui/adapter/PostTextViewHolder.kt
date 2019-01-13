package ru.bulat.mukhutdinov.sample.postslist.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.bulat.mukhutdinov.sample.R
import ru.bulat.mukhutdinov.sample.databinding.PostsTextItemBinding
import ru.bulat.mukhutdinov.sample.post.model.Post
import ru.bulat.mukhutdinov.sample.postslist.ui.PostsListViewModel
import java.util.Collections

class PostTextViewHolder(private val binding: PostsTextItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindTo(post: Post?, postsListViewModel: PostsListViewModel) {
        binding.post = post
        binding.postsListViewModel = postsListViewModel
        binding.avatar.clipToOutline = true

        val adapter = TagsAdapter(post?.tags ?: Collections.emptyList())
        binding.tags.adapter = adapter

        binding.tags.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)

        binding.executePendingBindings()
    }

    companion object {
        fun create(parent: ViewGroup): PostTextViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding: PostsTextItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.posts_text_item, parent, false)
            return PostTextViewHolder(binding)
        }
    }
}