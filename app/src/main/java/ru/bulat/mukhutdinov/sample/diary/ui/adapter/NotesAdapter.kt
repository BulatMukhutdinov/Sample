package ru.bulat.mukhutdinov.sample.diary.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.bulat.mukhutdinov.sample.R
import ru.bulat.mukhutdinov.sample.diary.model.Note
import ru.bulat.mukhutdinov.sample.infrastructure.common.ui.BaseViewHolder

class NotesAdapter : RecyclerView.Adapter<BaseViewHolder<*>>() {
    val notesWithDates: MutableList<Any> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> =
        when (viewType) {
            R.layout.diary_item_date -> DateViewHolder(parent)
            R.layout.diary_item_note -> NoteViewHolder(parent)
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (getItemViewType(position)) {
            R.layout.diary_item_date -> (holder as DateViewHolder).bindTo(notesWithDates[position] as String)
            R.layout.diary_item_note -> (holder as NoteViewHolder).bindTo(notesWithDates[position] as Note)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (notesWithDates[position] is Note) {
            R.layout.diary_item_note
        } else {
            R.layout.diary_item_date
        }
    }

    override fun getItemCount() =
        notesWithDates.size
}