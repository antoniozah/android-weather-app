package com.azachos.weatherapp.api

import com.azachos.weatherapp.utils.Constants.Companion.API_KEY
import com.azachos.weatherapp.utils.Constants.Companion.BASE_URL
import com.azachos.weatherapp.utils.Constants.Companion.HEADER_HOST
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {
        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            // Custom interceptor to set headers
            val headerInterceptor = Interceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .addHeader("X-RapidAPI-Key", API_KEY)
                    .addHeader("X-RapidAPI-Host", HEADER_HOST)
                    .build()

                chain.proceed(request)
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(headerInterceptor)
                .build()

            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        val api: ForecastApi by lazy {
            retrofit.create(ForecastApi::class.java)
        }
    }
}