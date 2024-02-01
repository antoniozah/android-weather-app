package com.azachos.weatherapp.datapersistence.forecast

import com.azachos.weatherapp.datapersistence.BaseRepository
import com.azachos.weatherapp.datapersistence.dao.ForecastDao
import com.azachos.weatherapp.model.forecast.ForecastResponse
import com.azachos.weatherapp.model.forecast.domain.MyForecast
import com.azachos.weatherapp.model.forecast.dto.ForecastDTO
import com.azachos.weatherapp.utils.Resource
import com.azachos.weatherapp.utils.toDomain
import com.azachos.weatherapp.utils.toEntity
import io.ktor.client.call.body
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ForecastRepositoryImpl @Inject constructor(
    private val forecastHttpRequest: ForecastHttpRequest,
    private val forecastDao: ForecastDao,
) : BaseRepository(), ForecastRepository {
    override suspend fun getLocationForecast(
        forecastParams: ForecastDTO
    ): Flow<Resource<MyForecast>> =
        executeRequest(
            apiCall = { forecastHttpRequest.getForecast(forecastParams) },
            httpResponse = {
                it.body<ForecastResponse>().apply {
                    forecastDao.insertForecast(this.toEntity())
                }.toDomain()
            },
            null
        )
}