package com.azachos.weatherapp.datapersistence

import android.util.Log
import com.azachos.weatherapp.utils.Constants.HTTP.API_KEY
import com.azachos.weatherapp.utils.Constants.HTTP.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class HttpHelper {
    init {
        instance = this
    }

    companion object {
        var instance: HttpHelper = HttpHelper()
    }

    val httpClient = HttpClient {
        install(Logging) {
             logger =  object: Logger {
                 override fun log(message: String) {
                     Log.d("WEATHER_APP", message)
                 }
             }
            level = LogLevel.ALL
        }

        install(ContentNegotiation) {
            json(Json {
                isLenient = true
                ignoreUnknownKeys = true
            })
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 45000
            socketTimeoutMillis = 45000
            connectTimeoutMillis = 45000
        }


        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
                host = BASE_URL
                contentType(ContentType.Application.Json)
            }
            header("X-RapidAPI-Key", API_KEY)
            header("X-RapidAPI-Host", BASE_URL)
        }

    }

    suspend fun request(path: String, httpMethod: HttpMethod, data: Any?) : HttpResponse =
        httpClient.request {
            method = httpMethod
            url(path)
            if(data != null) {
                setBody(data)
            }
        }.apply {
            return this
        }

}