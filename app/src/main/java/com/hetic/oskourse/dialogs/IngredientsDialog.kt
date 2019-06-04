package com.hetic.oskourse.dialogs

import android.app.Dialog
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.hetic.oskourse.R
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.core.content.edit
import kotlinx.android.synthetic.main.dialog_ingredient.*

class IngredientsDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val currentText: String
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflater.inflate(R.layout.dialog_ingredient, null))
                .setPositiveButton(R.string.confirm,
                    DialogInterface.OnClickListener { dialog, id ->
                        val newIngredient = ingredientPromptTextView.text.toString()

                        val sharedPreference = PreferenceManager.getDefaultSharedPreferences(this.context)

                        val ingredientsString = sharedPreference.getString("ingredients", "no ingredients")
                        val ingredientsList = ingredientsString.split(",").toMutableList()
                        ingredientsList.add(newIngredient)
                        sharedPreference.edit {
                            putString("ingredients", ingredientsList.toString().replace("[", "").replace("]", ""))
                        }

                        getDialog().cancel()
                    })
                .setNegativeButton(R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        getDialog().cancel()
                    })


            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}