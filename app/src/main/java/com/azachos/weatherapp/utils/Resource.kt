package com.azachos.weatherapp.utils

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val hasNetwork: Boolean? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T?, hasNetwork: Boolean?) :
        Resource<T>(data, message, hasNetwork)

    class Loading<T> : Resource<T>()

}