package com.azachos.weatherapp.datapersistence.forecast

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ForecastHttpRequestModule {
    @Provides
    @Singleton
    fun provideForecastHttpRequest(): ForecastHttpRequest {
        return ForecastHttpRequest()
    }
}