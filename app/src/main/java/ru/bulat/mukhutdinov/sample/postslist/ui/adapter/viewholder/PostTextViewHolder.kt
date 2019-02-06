package ru.bulat.mukhutdinov.sample.postslist.ui.adapter.viewholder

import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.posts_text_item.view.avatar
import kotlinx.android.synthetic.main.posts_text_item.view.body
import kotlinx.android.synthetic.main.posts_text_item.view.date
import kotlinx.android.synthetic.main.posts_text_item.view.tags
import kotlinx.android.synthetic.main.posts_text_item.view.title
import ru.bulat.mukhutdinov.sample.R
import ru.bulat.mukhutdinov.sample.infrastructure.common.ui.BaseViewHolder
import ru.bulat.mukhutdinov.sample.post.model.Post
import ru.bulat.mukhutdinov.sample.postslist.ui.adapter.TagsAdapter
import java.util.Collections

class PostTextViewHolder(private val picasso: Picasso, parent: ViewGroup, clickListener: ((Int) -> Unit))
    : BaseViewHolder<Post>(parent, R.layout.posts_text_item, clickListener) {

    private val avatar = itemView.avatar
    private val tags = itemView.tags
    private val date = itemView.date
    private val title = itemView.title
    private val body = itemView.body

    override fun bindTo(item: Post?) {
        super.bindTo(item)

        avatar.clipToOutline = true

        val adapter = TagsAdapter(item?.tags ?: Collections.emptyList())
        tags.adapter = adapter
        tags.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)

        date.text = item?.formattedDate
        title.text = item?.title
        body.text = item?.formattedBody

        picasso
            .load(item?.avatar)
            .placeholder(R.color.gray)
            .into(avatar)
    }

    override fun onViewRecycled() {
        super.onViewRecycled()
        picasso.cancelRequest(avatar)
    }
}