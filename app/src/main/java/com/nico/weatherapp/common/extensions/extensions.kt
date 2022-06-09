package com.nico.weatherapp.common.extensions

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.nico.weatherapp.data.service.Response

inline fun <reified T> Response<T>.doIfError(callback: (error: String?, throwable: Throwable?) -> Unit) {
    if (this is Response.Failure) {
        callback(message, throwable)
    }
}

inline fun <reified T> Response<T>.doIfSuccess(callback: (value: T) -> Unit) {
    if (this is Response.Success) {
        callback(value)
    }
}

fun inflateView(@LayoutRes layoutResId: Int, parent: ViewGroup, attachToRoot: Boolean): View =
    LayoutInflater.from(parent.context).inflate(layoutResId, parent, attachToRoot)

fun Activity.hideKeyboard() {
    // Check if no view has focus:
    var view = this.currentFocus
    if (view == null) {
        view = View(this)
    }

    val inputManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    view.clearFocus()

}