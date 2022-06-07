package com.nico.weatherapp.common.extensions

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