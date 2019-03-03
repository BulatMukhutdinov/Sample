package ru.bulat.mukhutdinov.sample.diary.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputEditText
import ru.bulat.mukhutdinov.sample.R
import java.io.Serializable

class NoteCreationDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(activity).inflate(R.layout.diary_create_note, null)
        val action = arguments?.getSerializable(ARG_ACTION) as Action

        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.create_note_title)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                val note = view.findViewById<TextInputEditText>(R.id.noteValue)
                action.invoke(note.text.toString())
            }
            .setNegativeButton(android.R.string.cancel) { _, _ -> }
            .setView(view)
            .create()
    }

    override fun onStart() {
        super.onStart()
        (dialog as AlertDialog)
            .getButton(AlertDialog.BUTTON_NEGATIVE)
            .setTextColor(ContextCompat.getColor(requireContext(), R.color.icon))
    }

    companion object {
        private const val ARG_ACTION = "action_arg"

        fun newInstance(action: ((text: String) -> Unit)) =
            NoteCreationDialog().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_ACTION, object : Action {
                        override fun invoke(text: String) {
                            action(text)
                        }
                    })
                }
            }

        interface Action : Serializable {
            fun invoke(text: String)
        }
    }
}