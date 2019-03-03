package ru.bulat.mukhutdinov.sample.diary.ui.adapter

import android.view.ViewGroup
import kotlinx.android.synthetic.main.diary_item_note.view.note
import ru.bulat.mukhutdinov.sample.R
import ru.bulat.mukhutdinov.sample.diary.model.Note
import ru.bulat.mukhutdinov.sample.infrastructure.common.ui.BaseViewHolder

class NoteViewHolder(parent: ViewGroup)
    : BaseViewHolder<Note>(parent, R.layout.diary_item_note) {

    private val note = itemView.note

    override fun bindTo(item: Note?) {
        super.bindTo(item)
        if (item is Note) {
            note.text = item.text
        }
    }
}