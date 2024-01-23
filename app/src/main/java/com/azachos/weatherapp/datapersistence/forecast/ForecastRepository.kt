package com.azachos.weatherapp.datapersistence.forecast

import com.azachos.weatherapp.model.ForecastResponse
import com.azachos.weatherapp.model.forecast.dto.ForecastDTO
import com.azachos.weatherapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ForecastRepository {
    suspend fun getLocationForecast(forecastParams: ForecastDTO) : Flow<Resource<ForecastResponse>>
}