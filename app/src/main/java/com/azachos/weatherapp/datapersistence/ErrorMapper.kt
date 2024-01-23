package com.azachos.weatherapp.datapersistence

interface ErrorMapper {
    fun mapErrorToMessage(errorCode: String): String
}