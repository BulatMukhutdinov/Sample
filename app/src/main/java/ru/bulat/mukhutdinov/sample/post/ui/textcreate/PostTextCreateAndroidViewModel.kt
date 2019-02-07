package ru.bulat.mukhutdinov.sample.post.ui.textcreate

import ru.bulat.mukhutdinov.sample.infrastructure.common.ui.BaseAndroidViewModel
import ru.bulat.mukhutdinov.sample.infrastructure.util.data.DataStateLiveData

class PostTextCreateAndroidViewModel()
    : BaseAndroidViewModel(), PostTextCreateViewModel {

    override fun create(title: String, body: String, tags: List<String>): DataStateLiveData<Unit> {
        TODO()
    }
}