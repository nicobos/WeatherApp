package com.nico.weatherapp.data.service

sealed class Response<out T> {
    data class Success<out T>(val value: T) : Response<T>()
    data class Failure(val message: String? = null, val throwable: Throwable? = null) : Response<Nothing>()
}