package com.azachos.weatherapp.datapersistence.forecast

import com.azachos.weatherapp.datapersistence.HttpHelper
import com.azachos.weatherapp.model.forecast.dto.ForecastDTO
import com.azachos.weatherapp.utils.Constants.API_ENDPOINT.GET_FORECAST
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpMethod
import javax.inject.Inject

class ForecastHttpRequest @Inject constructor(){
    suspend fun getForecast(params: ForecastDTO) : HttpResponse =
        HttpHelper.instance.request(
            GET_FORECAST+"?q=${params.location}&days=${params.days}",
            HttpMethod.Get,
            null
        )
}