package com.azachos.weatherapp.datapersistence.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "forecast_table")
data class ForecastDataEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("forecast_table_id")
    val id: Int,
    @Embedded
    val current : CurrentEntity,
    @Embedded
    val forecast: ForecastEntity,
    @Embedded
    val location: LocationEntity
)