package com.azachos.weatherapp.model

import kotlinx.serialization.Serializable

@Serializable
data class Forecast(
    val forecastday: List<Forecastday>
)