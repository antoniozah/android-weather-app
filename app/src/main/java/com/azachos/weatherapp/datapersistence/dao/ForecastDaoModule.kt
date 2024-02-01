package com.azachos.weatherapp.datapersistence.dao

import com.azachos.weatherapp.datapersistence.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ForecastDaoModule {

    @Provides
    @Singleton
    fun provideForecastDaoModule(appDatabase: AppDatabase) : ForecastDao =
        appDatabase.forecastDao()

}