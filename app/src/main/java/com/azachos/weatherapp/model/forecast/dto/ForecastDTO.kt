package com.azachos.weatherapp.model.forecast.dto

import kotlinx.serialization.Serializable

@Serializable
data class ForecastDTO(
    val location: String,
    val days: Int
)