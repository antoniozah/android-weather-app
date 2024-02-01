package com.azachos.weatherapp.datapersistence.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import kotlinx.serialization.Serializable

@Serializable
data class ForecastDayEntity(
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name="date_epoch")
    val dateEpoch: Int,
    @ColumnInfo(name="day")
    val day: DayEntity,
    @Embedded
    val hourList: HourEmbed
)

@Serializable
data class HourEmbed(
    val hour: List<HourEntity>
)