package ru.bulat.mukhutdinov.sample.diary.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.diary.createNewNote
import kotlinx.android.synthetic.main.diary.notes
import org.koin.androidx.viewmodel.ext.viewModel
import ru.bulat.mukhutdinov.sample.R
import ru.bulat.mukhutdinov.sample.diary.ui.adapter.NoteDiffUtilCallback
import ru.bulat.mukhutdinov.sample.diary.ui.adapter.NotesAdapter
import ru.bulat.mukhutdinov.sample.infrastructure.common.ui.BaseFragment
import ru.bulat.mukhutdinov.sample.infrastructure.extension.observeViewState
import timber.log.Timber

class DiaryFragment : BaseFragment<DiaryViewModel>() {

    override val viewModel by viewModel<DiaryAndroidViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.diary, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNewNoteClickListener()
        setupNotes()
    }

    private fun setupNotes() {
        val adapter = NotesAdapter()

        notes.adapter = adapter
        notes.layoutManager = LinearLayoutManager(context)

        viewModel.notesWithDates.observeViewState(
            owner = viewLifecycleOwner,
            dataCallback = {
                val diffUtilCallback = NoteDiffUtilCallback(adapter.notesWithDates, it)
                val diffResult = DiffUtil.calculateDiff(diffUtilCallback)

                adapter.notesWithDates.clear()
                adapter.notesWithDates.addAll(it)
                diffResult.dispatchUpdatesTo(adapter)
            },
            errorCallback = { Timber.e(it) }
        )
    }

    private fun setupNewNoteClickListener() {
        createNewNote.setOnClickListener {
            NoteCreationDialog
                .newInstance { text ->
                    viewModel.addNote(text).observeViewState(
                        owner = viewLifecycleOwner,
                        completeCallback = {
                            Timber.d("")
                        },
                        errorCallback = { Timber.e(it) }
                    )
                }
                .show(childFragmentManager, null)
        }
    }
}