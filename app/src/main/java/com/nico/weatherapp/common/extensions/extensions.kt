package com.nico.weatherapp.common.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

fun <T> String.fromGson(classOfT: Class<T>) = Gson().fromJson(this, classOfT)
fun Any.toGson() = Gson().toJson(this)
fun <T> JsonElement.fromGson(classOfT: Class<T>) = Gson().fromJson(this, classOfT)