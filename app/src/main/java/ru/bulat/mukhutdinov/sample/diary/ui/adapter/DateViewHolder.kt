package ru.bulat.mukhutdinov.sample.diary.ui.adapter

import android.view.ViewGroup
import kotlinx.android.synthetic.main.diary_item_date.view.date
import ru.bulat.mukhutdinov.sample.R
import ru.bulat.mukhutdinov.sample.infrastructure.common.ui.BaseViewHolder

class DateViewHolder(parent: ViewGroup)
    : BaseViewHolder<String>(parent, R.layout.diary_item_date) {

    private val date = itemView.date

    override fun bindTo(item: String?) {
        super.bindTo(item)
        date.text = item
    }
}