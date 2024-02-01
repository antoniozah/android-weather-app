package com.azachos.weatherapp.model.forecast

import kotlinx.serialization.Serializable

@Serializable
data class ForecastResponse(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)