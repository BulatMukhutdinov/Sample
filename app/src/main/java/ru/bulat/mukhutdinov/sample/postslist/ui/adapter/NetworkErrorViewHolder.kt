package ru.bulat.mukhutdinov.sample.postslist.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ru.bulat.mukhutdinov.sample.R
import ru.bulat.mukhutdinov.sample.databinding.PostsErrorItemBinding
import ru.bulat.mukhutdinov.sample.postslist.ui.PostsListViewModel

class NetworkErrorViewHolder(private val binding: PostsErrorItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindTo(postsListViewModel: PostsListViewModel) {
        binding.postsListViewModel = postsListViewModel

        binding.executePendingBindings()
    }

    companion object {
        fun create(parent: ViewGroup): NetworkErrorViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding: PostsErrorItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.posts_error_item, parent, false)
            return NetworkErrorViewHolder(binding)
        }
    }
}