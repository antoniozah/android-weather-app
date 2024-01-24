package com.azachos.weatherapp.datapersistence.forecast

import com.azachos.weatherapp.datapersistence.BaseRepository
import com.azachos.weatherapp.model.ForecastResponse
import com.azachos.weatherapp.model.forecast.dto.ForecastDTO
import com.azachos.weatherapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ForecastRepositoryImpl @Inject constructor(
    private val forecastHttpRequest: ForecastHttpRequest,
) : BaseRepository(), ForecastRepository {
    override suspend fun getLocationForecast(
        forecastParams: ForecastDTO
    ): Flow<Resource<ForecastResponse>> =
        executeRequest(
            apiCall = { forecastHttpRequest.getForecast(forecastParams) },
            mapper = { httpResponse -> mapResponse<ForecastResponse>(httpResponse) },
            null
        )
}