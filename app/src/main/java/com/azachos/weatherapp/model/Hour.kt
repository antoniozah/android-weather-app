package com.azachos.weatherapp.model

import kotlinx.serialization.Serializable

@Serializable
data class Hour(
    val chance_of_rain: Int,
    val chance_of_snow: Int,
    val cloud: Int,
    val condition: Condition,
    val dewpoint_c: Double,
    val dewpoint_f: Double,
    val feelslike_c: Double,
    val humidity: Int,
    val is_day: Int,
    val temp_c: Double,
    val time: String,
    val time_epoch: Int,
    val uv: Double,
    val will_it_rain: Int,
    val will_it_snow: Int,
    val wind_degree: Int,
    val wind_dir: String,
    val wind_kph: Double,
)