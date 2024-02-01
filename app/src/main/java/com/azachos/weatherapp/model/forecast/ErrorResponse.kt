package com.azachos.weatherapp.model.forecast

import kotlinx.serialization.Serializable

@Serializable
data class ErrorHttpResponse(
    val error: ErrorResponse?
)

@Serializable
data class ErrorResponse(
    val code: Int,
    val message: String
)