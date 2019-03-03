package ru.bulat.mukhutdinov.sample.diary.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.bulat.mukhutdinov.sample.diary.model.Note

class NoteDiffUtilCallback(private val oldList: List<Any>, private val newList: List<Any>)
    : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldNote = oldList[oldItemPosition]
        val newNote = newList[newItemPosition]

        return when {
            oldNote is Note && newNote is Note -> oldNote.id == newNote.id
            oldNote is String && newNote is String -> oldNote == newNote
            else -> false
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldNote = oldList[oldItemPosition]
        val newNote = newList[newItemPosition]
        return oldNote == newNote
    }
}