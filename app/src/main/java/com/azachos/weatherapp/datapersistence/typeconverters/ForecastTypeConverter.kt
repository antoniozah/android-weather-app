package com.azachos.weatherapp.datapersistence.typeconverters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.azachos.weatherapp.datapersistence.entities.ForecastDayEntity
import com.azachos.weatherapp.datapersistence.entities.HourEntity
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@ProvidedTypeConverter
class ForecastTypeConverter {
    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun forecastDayListToString(list: List<ForecastDayEntity>): String {
        return list.let { json.encodeToString(it) }
    }

    @TypeConverter
    fun StringToForecastDayList(listString: String): List<ForecastDayEntity> {
        return listString.let { json.decodeFromString<List<ForecastDayEntity>>(it) }
    }

    @TypeConverter
    fun HourlyListToString(list: List<HourEntity>): String {
        return list.let { json.encodeToString(it) }
    }

    @TypeConverter
    fun StringToHourlyList(listString: String): List<HourEntity> {
        return listString.let { json.decodeFromString<List<HourEntity>>(it) }
    }
}