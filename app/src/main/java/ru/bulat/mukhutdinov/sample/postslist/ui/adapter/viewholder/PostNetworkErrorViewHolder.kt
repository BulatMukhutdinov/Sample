package ru.bulat.mukhutdinov.sample.postslist.ui.adapter.viewholder

import android.view.ViewGroup
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.posts_error_item.view.description
import kotlinx.android.synthetic.main.posts_error_item.view.retry
import ru.bulat.mukhutdinov.sample.R
import ru.bulat.mukhutdinov.sample.infrastructure.common.model.NetworkState
import ru.bulat.mukhutdinov.sample.infrastructure.common.ui.BaseViewHolder
import ru.bulat.mukhutdinov.sample.post.model.Post
import ru.bulat.mukhutdinov.sample.postslist.ui.PostsListViewModel

class PostNetworkErrorViewHolder(parent: ViewGroup)
    : BaseViewHolder<Post>(parent, R.layout.posts_error_item) {

    private val description = itemView.description
    private val retry = itemView.retry

    fun bindTo(item: PostsListViewModel) {
        super.bindTo(null)

        retry.setOnClickListener { item.retry() }

        item.networkState.observe(this, Observer {
            retry.isEnabled = it != NetworkState.Loading
        })

        item.errorText.observe(this, Observer {
            description.text = it
        })
    }
}