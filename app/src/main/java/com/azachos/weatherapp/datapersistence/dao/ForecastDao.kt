package com.azachos.weatherapp.datapersistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.azachos.weatherapp.datapersistence.entities.ForecastDataEntity

@Dao
interface ForecastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecast(forecast: ForecastDataEntity)

    @Query("SELECT * FROM forecast_table")
    suspend fun getForecastData(): ForecastDataEntity
}