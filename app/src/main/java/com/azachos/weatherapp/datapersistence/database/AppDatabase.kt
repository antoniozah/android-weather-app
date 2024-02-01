package com.azachos.weatherapp.datapersistence.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.azachos.weatherapp.datapersistence.dao.ForecastDao
import com.azachos.weatherapp.datapersistence.entities.ForecastDataEntity
import com.azachos.weatherapp.datapersistence.typeconverters.ForecastTypeConverter

@Database([ForecastDataEntity::class], version = 1, exportSchema = false)
@TypeConverters(ForecastTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun forecastDao(): ForecastDao

}