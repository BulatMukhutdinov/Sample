package ru.bulat.mukhutdinov.sample.postslist.ui.adapter.viewholder

import android.view.ViewGroup
import android.widget.TextView
import com.jakewharton.rxbinding3.view.clicks
import com.squareup.picasso.Picasso
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.posts_text_item.view.avatar
import kotlinx.android.synthetic.main.posts_text_item.view.blogName
import kotlinx.android.synthetic.main.posts_text_item.view.body
import kotlinx.android.synthetic.main.posts_text_item.view.like
import kotlinx.android.synthetic.main.posts_text_item.view.tags
import kotlinx.android.synthetic.main.posts_text_item.view.title
import ru.bulat.mukhutdinov.sample.R
import ru.bulat.mukhutdinov.sample.infrastructure.common.ui.BaseViewHolder
import ru.bulat.mukhutdinov.sample.infrastructure.extension.hide
import ru.bulat.mukhutdinov.sample.infrastructure.extension.show
import ru.bulat.mukhutdinov.sample.post.model.Post
import ru.bulat.mukhutdinov.sample.post.model.TextPost
import ru.bulat.mukhutdinov.sample.postslist.ui.adapter.TagsAdapter
import java.util.concurrent.TimeUnit

class PostTextViewHolder(private val picasso: Picasso, parent: ViewGroup, clickListener: ((Int) -> Unit))
    : BaseViewHolder<Post>(parent, R.layout.posts_text_item, clickListener) {

    private val avatar = itemView.avatar
    private val blogName = itemView.blogName
    private val tags = itemView.tags
    private val title = itemView.title
    private val body = itemView.body
    private val like = itemView.like

    private lateinit var disposable: Disposable

    override fun bindTo(item: Post?) {
        super.bindTo(item)
        if (item is TextPost) {
            setupTags(item)
            showIfNotEmpty(title, item.title)
            showIfNotEmpty(body, item.formattedBody)
            setupLike(item)

            blogName.text = item.blogName

            avatar.clipToOutline = true
            picasso
                .load(item.avatar)
                .placeholder(R.color.gray)
                .into(avatar)
        }
    }

    private fun setupTags(item: Post) {
        if (item.tags.isEmpty()) {
            tags.hide()
        } else {
            tags.show()
            val adapter = TagsAdapter(item.tags)
            tags.adapter = adapter
        }
    }

    private fun showIfNotEmpty(textView: TextView, text: String) {
        if (text.isEmpty()) {
            textView.hide()
        } else {
            textView.show()
            textView.text = text
        }
    }

    private fun setupLike(item: Post) {

        fun updateLikeState() {
            val stateSet = if (item.isLiked) {
                intArrayOf(android.R.attr.state_active)
            } else {
                intArrayOf(android.R.attr.state_active * -1)
            }

            like.setImageState(stateSet, true)
        }

        updateLikeState()

        disposable = like.clicks()
            .throttleFirst(400, TimeUnit.MILLISECONDS)
            .subscribe {
                item.isLiked = !item.isLiked
                updateLikeState()
            }
    }

    override fun onViewRecycled() {
        super.onViewRecycled()
        picasso.cancelRequest(avatar)

        if (::disposable.isInitialized) {
            disposable.dispose()
        }
    }
}