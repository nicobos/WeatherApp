package com.nico.weatherapp.ui.dialogs

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nico.weatherapp.R

class DialogManager(val context: Context): IDialogManager{

    override fun showDialogPermissionRationale(
        onNeutralButton: () -> Unit
    ) {
        MaterialAlertDialogBuilder(context)
            .setTitle(context.getString(R.string.permission_rationale_title))
            .setMessage(context.getString(R.string.permission_rationale_description))
            .setNeutralButton(context.getString(R.string.permission_rationale_neutral_button)){ dialog, _ ->
                onNeutralButton()
                dialog.dismiss()
            }
            .show()
    }

}