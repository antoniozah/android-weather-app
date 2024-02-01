package com.azachos.weatherapp.datapersistence.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import kotlinx.serialization.Serializable

@Serializable
data class HourEntity(
    @ColumnInfo(name = "chance_of_rain")
    val chanceOfRain: Int,
    @ColumnInfo(name = "cloud")
    val cloud: Int,
    @Embedded
    val condition: ConditionEntity,
    @ColumnInfo(name = "feelslike_c")
    val feelsLikeC: Double,
    @ColumnInfo(name = "humidity")
    val humidity: Int,
    @ColumnInfo(name = "is_day")
    val isDay: Int,
    @ColumnInfo(name = "temp_c")
    val tempC: Double,
    @ColumnInfo(name = "time")
    val time: String,
    @ColumnInfo(name = "time_epoch")
    val timeEpoch: Int
)
