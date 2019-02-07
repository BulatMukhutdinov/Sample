package ru.bulat.mukhutdinov.sample.postslist.ui.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import com.squareup.picasso.Picasso
import ru.bulat.mukhutdinov.sample.R
import ru.bulat.mukhutdinov.sample.infrastructure.common.model.NetworkState
import ru.bulat.mukhutdinov.sample.infrastructure.common.model.Status
import ru.bulat.mukhutdinov.sample.infrastructure.common.ui.BaseViewHolder
import ru.bulat.mukhutdinov.sample.infrastructure.util.data.DiffUtilItemCallback
import ru.bulat.mukhutdinov.sample.post.model.Post
import ru.bulat.mukhutdinov.sample.postslist.ui.PostsListViewModel
import ru.bulat.mukhutdinov.sample.postslist.ui.adapter.viewholder.PostNetworkErrorViewHolder
import ru.bulat.mukhutdinov.sample.postslist.ui.adapter.viewholder.PostTextViewHolder

class PostsAdapter(
    private val picasso: Picasso,
    private val postsListViewModel: PostsListViewModel,
    private val clickListener: ((Int) -> Unit)
) : PagedListAdapter<Post, BaseViewHolder<*>>(DiffUtilItemCallback<Post>()) {

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> =
        when (viewType) {
            R.layout.posts_text_item -> PostTextViewHolder(picasso, parent, clickListener)
            R.layout.posts_error_item -> PostNetworkErrorViewHolder(parent)
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (getItemViewType(position)) {
            R.layout.posts_text_item -> (holder as PostTextViewHolder).bindTo(getItem(position))
            R.layout.posts_error_item -> (holder as PostNetworkErrorViewHolder).bindTo(postsListViewModel)
        }
    }

    private fun hasExtraRow() = networkState != null && networkState?.status == Status.FAILED

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.posts_error_item
        } else {
            R.layout.posts_text_item
        }
    }

    fun getItemAt(index: Int) =
        getItem(index)

    fun updateNetworkState(newNetworkState: NetworkState) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun onViewRecycled(holder: BaseViewHolder<*>) {
        super.onViewRecycled(holder)
        holder.onViewRecycled()
    }
}