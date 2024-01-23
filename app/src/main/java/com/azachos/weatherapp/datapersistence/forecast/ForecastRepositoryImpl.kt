package com.azachos.weatherapp.datapersistence.forecast

import android.util.Log
import com.azachos.weatherapp.model.ForecastResponse
import com.azachos.weatherapp.model.forecast.dto.ForecastDTO
import com.azachos.weatherapp.utils.Constants.ERROR.GENERIC_ERROR_MESSAGE
import com.azachos.weatherapp.utils.Resource
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ForecastRepositoryImpl @Inject constructor(
    private val forecastHttpRequest: ForecastHttpRequest
) : ForecastRepository {
    override suspend fun getLocationForecast(
        forecastParams: ForecastDTO
    ): Flow<Resource<ForecastResponse>> = flow{
        try {
            emit(Resource.Loading())
            val response: HttpResponse = forecastHttpRequest.getForecast(forecastParams)
            val decodedResponse = response.body<ForecastResponse>()
            when (response.status.isSuccess()) {
                true -> {
                    emit(Resource.Success(decodedResponse))
                }
                false -> {
                    emit(Resource.Error(GENERIC_ERROR_MESSAGE, null))
                }
            }
        } catch (error: Exception) {
            Log.d("WEATHER_APP", "error $error.localizedMessage")
            emit(Resource.Error(error.localizedMessage ?: GENERIC_ERROR_MESSAGE, null))
        }
    }
}