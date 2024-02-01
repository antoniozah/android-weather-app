package com.azachos.weatherapp.datapersistence.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.azachos.weatherapp.model.forecast.Condition

data class CurrentEntity(
    @ColumnInfo(name="cloud")
    val cloud: Int = 0,
    @Embedded
    val condition: ConditionEntity,
    @ColumnInfo(name="feelslike_c")
    val feelsLikeC: Double,
    @ColumnInfo(name="humidity")
    val humidity: Int,
    @ColumnInfo(name="is_day")
    val isDay: Int,
    @ColumnInfo(name="last_updated")
    val lastUpdated: String,
)
