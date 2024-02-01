package com.azachos.weatherapp.datapersistence.entities

import androidx.room.Embedded

data class ForecastEntity(
    @Embedded
    val forecastDayEmbed: ForecastDayEmbed
)

data class ForecastDayEmbed(
    val forecastDay: List<ForecastDayEntity>
)