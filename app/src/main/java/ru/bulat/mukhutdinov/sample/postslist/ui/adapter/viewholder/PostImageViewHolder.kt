package ru.bulat.mukhutdinov.sample.postslist.ui.adapter.viewholder

import android.net.TrafficStats
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.posts_text_item.view.avatar
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.bulat.mukhutdinov.sample.R
import ru.bulat.mukhutdinov.sample.databinding.PostsTextItemBinding
import ru.bulat.mukhutdinov.sample.infrastructure.common.ui.BaseViewHolder
import ru.bulat.mukhutdinov.sample.post.model.Post
import ru.bulat.mukhutdinov.sample.postslist.ui.adapter.TagsAdapter
import java.util.Collections

class PostImageViewHolder(private val binding: PostsTextItemBinding) : BaseViewHolder<Post>(binding.root), KoinComponent {

    private val picasso: Picasso by inject()
    private val avatar = itemView.avatar

    fun bindTo(post: Post?) {
        binding.post = post
        binding.avatar.clipToOutline = true

        val adapter = TagsAdapter(post?.tags ?: Collections.emptyList())
        binding.tags.adapter = adapter

        binding.tags.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)

        picasso.load(post?.avatar)
            .placeholder(R.color.gray)
            .into(avatar)

        binding.executePendingBindings()
    }

    override fun onViewRecycled() {
        picasso.cancelRequest(avatar)
    }

    companion object {
        fun create(parent: ViewGroup): PostImageViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding: PostsTextItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.posts_text_item, parent, false)
            return PostImageViewHolder(binding)
        }
    }
}