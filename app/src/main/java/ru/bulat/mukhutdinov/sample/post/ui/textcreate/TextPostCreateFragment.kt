package ru.bulat.mukhutdinov.sample.post.ui.textcreate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.post_create_text.*
import org.koin.androidx.viewmodel.ext.viewModel
import ru.bulat.mukhutdinov.sample.R
import ru.bulat.mukhutdinov.sample.infrastructure.common.ui.BaseFragment
import ru.bulat.mukhutdinov.sample.infrastructure.extension.observeViewState
import ru.bulat.mukhutdinov.sample.post.ui.PostCreateActivity
import timber.log.Timber

class TextPostCreateFragment : BaseFragment<TextPostCreateViewModel>() {

    override val viewModel by viewModel<TextPostCreateAndroidViewModel>()

    private lateinit var compositeDisposable: CompositeDisposable

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.post_create_text, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        compositeDisposable = CompositeDisposable()
        setupTextChanges()
        setupPost()
    }

    private fun setupTextChanges() {
        compositeDisposable.add(
                titleValue.textChanges()
                        .map { it.toString() }
                        .subscribe { viewModel.updateTitle(it) }
        )

        compositeDisposable.add(
                bodyValue.textChanges()
                        .map { it.toString() }
                        .subscribe { viewModel.updateBody(it) }
        )
    }

    private fun setupPost() {
        viewModel.isPostEnabled.observeViewState(
                owner = viewLifecycleOwner,
                dataCallback = { post.isEnabled = it }
        )

        post.setOnClickListener {
            val title = titleValue.text.toString()
            val body = bodyValue.text.toString()

            viewModel.create(title = title, body = body, tags = emptyList()).observeViewState(
                    owner = viewLifecycleOwner,
                    completeCallback = { returnOkResult() },
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

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.dispose()
    }
}