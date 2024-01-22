package com.azachos.weatherapp.datapersistence

import com.azachos.weatherapp.model.ForecastResponse
import com.azachos.weatherapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ForecastRepository {
    suspend fun getLocationForecast(location: String, days: Int) : Flow<Resource<ForecastResponse>>
}