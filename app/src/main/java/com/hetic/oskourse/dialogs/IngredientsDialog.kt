package com.hetic.oskourse.dialogs

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.hetic.oskourse.R
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.core.content.edit
import kotlinx.android.synthetic.main.dialog_ingredient.*
import java.util.*
import kotlin.concurrent.schedule
import android.widget.EditText

class IngredientsDialog : DialogFragment() {

    var currentText: String = ""
    private var editIngredient: EditText? = null

    internal lateinit var listener: NoticeDialogListener

    interface NoticeDialogListener {
        fun onDialogPositiveClick(toAdd: String)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        println(ingredientPromptTextView)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = context as NoticeDialogListener
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException((context.toString() +
                    " must implement NoticeDialogListener"))
        }

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflater.inflate(R.layout.dialog_ingredient, null))
                .setPositiveButton(R.string.confirm,
                    DialogInterface.OnClickListener { dialog, id ->
                        val f = dialog as Dialog
                        //This is the input I can't get text from
                        val inputTemp = f.findViewById<View>(R.id.ingredientPromptTextView) as EditText
                        val nuIngredient = inputTemp.text.toString()

                        listener.onDialogPositiveClick(nuIngredient)

                    })
                .setNegativeButton(R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        getDialog().cancel()
                    })

            editIngredient = view?.findViewById(R.id.ingredientPromptTextView)
            builder.create()

        } ?: throw IllegalStateException("Activity cannot be null")
    }

}