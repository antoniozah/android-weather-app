package com.azachos.weatherapp.api

import com.azachos.weatherapp.model.ForecastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastApi {

    @GET("/forecast.json")
    suspend fun getForecast(
        @Query("q")
        locationValue: String = "Thessaloniki",
        @Query("days")
        forecastDays: Int = 3
    ) : Response<ForecastResponse>

}