package ru.bulat.mukhutdinov.sample.postslist.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TagsAdapter(private val tags: List<String>) : RecyclerView.Adapter<TagViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder =
        TagViewHolder.create(parent)

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.bindTo(tags[position])
    }

    override fun getItemCount() =
        tags.size
}