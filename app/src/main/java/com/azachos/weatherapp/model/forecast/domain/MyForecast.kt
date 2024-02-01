package com.azachos.weatherapp.model.forecast.domain

data class MyForecast(
    val date: String,
    val dateEpoch: Int,
    val locationCountry: String,
    val localtime: String,
    val locationName: String,
    val locationRegion: String,
    val conditionIcon: String,
    val conditionText: String
)
