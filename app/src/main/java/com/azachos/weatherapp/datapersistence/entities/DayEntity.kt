package com.azachos.weatherapp.datapersistence.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.azachos.weatherapp.model.forecast.Condition
import kotlinx.serialization.Serializable

@Serializable
data class DayEntity(
    @ColumnInfo(name="avghumidity")
    val avgHumidity: Int,
    @ColumnInfo(name="avgtemp_c")
    val avgTempC: Double,
    @Embedded
    val condition: ConditionEntity,
    @ColumnInfo(name="maxtemp_c")
    val maxTempC: Double,
    @ColumnInfo(name="mintemp_c")
    val minTempC: Double
)
