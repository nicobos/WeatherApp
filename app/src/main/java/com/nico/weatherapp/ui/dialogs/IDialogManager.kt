package com.nico.weatherapp.ui.dialogs

interface IDialogManager {

    fun showDialogPermissionRationale(onNeutralButton: () -> Unit)
}