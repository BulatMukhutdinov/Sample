package ru.bulat.mukhutdinov.sample.post.ui.textcreate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.post_create_text.body
import kotlinx.android.synthetic.main.post_create_text.create
import kotlinx.android.synthetic.main.post_create_text.title
import org.koin.androidx.viewmodel.ext.viewModel
import ru.bulat.mukhutdinov.sample.R
import ru.bulat.mukhutdinov.sample.infrastructure.common.ui.BaseFragment
import ru.bulat.mukhutdinov.sample.infrastructure.extension.observeViewState
import ru.bulat.mukhutdinov.sample.post.ui.PostCreateActivity
import timber.log.Timber

class PostTextCreateFragment : BaseFragment<PostTextCreateViewModel>() {

    override val viewModel
        by viewModel<PostTextCreateAndroidViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.post_create_text, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        create.setOnClickListener {
            val title = title.text.toString()
            val body = body.text.toString()

            viewModel.create(title = title, body = body, tags = emptyList()).observeViewState(
                owner = viewLifecycleOwner,
                completeCallback = { returnOkResult() },
                loadingCallback = { isLoading -> },
                errorCallback = {
                    returnCanceledResult()
                    Timber.e(it)
                }
            )
        }
    }

    private fun returnOkResult() {
        activity?.let {
            (it as PostCreateActivity).returnOkResult()
        }
    }

    private fun returnCanceledResult() {
        activity?.let {
            (it as PostCreateActivity).returnCanceledResult()
        }
    }
}