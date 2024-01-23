package com.azachos.weatherapp.datapersistence

import com.azachos.weatherapp.MyApp.Companion.applicationContent
import com.azachos.weatherapp.R
import com.azachos.weatherapp.utils.Constants.ERROR.NO_CONNECTION_ERROR_MESSAGE
import javax.inject.Inject

class ErrorMapperImpl @Inject constructor() : ErrorMapper {
    override fun mapErrorToMessage(errorCode: String): String {
        return when(errorCode) {
            NO_CONNECTION_ERROR_MESSAGE -> applicationContent.getString(R.string.no_connection_error_message)
            else -> errorCode
        }
    }
}