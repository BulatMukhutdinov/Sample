package ru.bulat.mukhutdinov.sample.postslist.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ru.bulat.mukhutdinov.sample.R
import ru.bulat.mukhutdinov.sample.databinding.PostsTagItemBinding

class TagViewHolder(private val binding: PostsTagItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindTo(tag: String?) {
        binding.tag = tag

        binding.executePendingBindings()
    }

    companion object {
        fun create(parent: ViewGroup): TagViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding: PostsTagItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.posts_tag_item, parent, false)
            return TagViewHolder(binding)
        }
    }
}