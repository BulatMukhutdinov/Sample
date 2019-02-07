package ru.bulat.mukhutdinov.sample.post.ui.textcreate

import ru.bulat.mukhutdinov.sample.infrastructure.common.ui.BaseViewModel
import ru.bulat.mukhutdinov.sample.infrastructure.util.data.DataStateLiveData

interface PostTextCreateViewModel : BaseViewModel {

    fun create(title: String, body: String, tags: List<String>): DataStateLiveData<Unit>
}