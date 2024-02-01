package com.azachos.weatherapp.model.forecast

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastDay(
    val date: String,
    @SerialName("date_epoch")
    val dateEpoch: Int,
    val day: Day,
    val hour: List<Hour>
)