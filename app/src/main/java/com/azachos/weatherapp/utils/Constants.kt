package com.azachos.weatherapp.utils

object Constants {
    object HTTP {
        const val API_KEY = "7cacdcaf92msh1a58355524cfa81p11bb14jsnfbd4ab44001b"
        const val BASE_URL = "weatherapi-com.p.rapidapi.com"
    }

    object API {
        const val GET_FORECAST = "forecast.json"
    }

    object ERROR {
        const val GENERIC_ERROR_MESSAGE = "Something went wrong!"
        const val NO_CONNECTION_ERROR_MESSAGE = "No internet Connection!"
    }

    object DATABASE {
        const val APPLICATION_DATABASE_NAME = "weather_app_localDB"
    }
}