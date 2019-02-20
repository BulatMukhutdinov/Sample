package ru.bulat.mukhutdinov.sample.post.ui.textcreate

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.bulat.mukhutdinov.sample.infrastructure.common.ui.BaseAndroidViewModel
import ru.bulat.mukhutdinov.sample.infrastructure.extension.postToViewStateLiveData
import ru.bulat.mukhutdinov.sample.infrastructure.util.data.DataStateLiveData
import ru.bulat.mukhutdinov.sample.post.usecase.CreateTextPostUseCase
import ru.bulat.mukhutdinov.sample.post.usecase.ValidateTextPostUseCase

class TextPostCreateAndroidViewModel(
        private val validateTextPostUseCase: ValidateTextPostUseCase,
        private val createTextPostUseCase: CreateTextPostUseCase
) : BaseAndroidViewModel(), TextPostCreateViewModel {

    override val isPostEnabled: DataStateLiveData<Boolean> = DataStateLiveData()

    private var title = ""
    private var body = ""

    override fun updateTitle(title: String) {
        this.title = title
        validate()
    }

    override fun updateBody(body: String) {
        this.body = body
        validate()
    }

    private fun validate() {
        validateTextPostUseCase.execute(title, body)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .postToViewStateLiveData(isPostEnabled)
                .disposeOnCleared()
    }

    override fun create(title: String, body: String, tags: List<String>): DataStateLiveData<Unit> {
        val createPostState = DataStateLiveData.createForSingle<Unit>()

        createTextPostUseCase.execute(title, body, tags)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .postToViewStateLiveData(createPostState)
                .disposeOnCleared()

        return createPostState
    }
}