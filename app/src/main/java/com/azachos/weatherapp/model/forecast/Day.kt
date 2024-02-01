package com.azachos.weatherapp.model.forecast

import kotlinx.serialization.Serializable

@Serializable
data class Day(
    val avghumidity: Int,
    val avgtemp_c: Double,
    val avgtemp_f: Double,
    val avgvis_km: Double,
    val condition: Condition,
    val daily_chance_of_rain: Int,
    val daily_chance_of_snow: Int,
    val daily_will_it_rain: Int,
    val daily_will_it_snow: Int,
    val maxtemp_c: Double,
    val maxwind_kph: Double,
    val mintemp_c: Double,
    val uv: Double
)