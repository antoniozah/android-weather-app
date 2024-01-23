package com.azachos.weatherapp.datapersistence.forecast

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ForecastRepositoryModule {

    @Binds
    abstract fun provideForecastRepository(impl: ForecastRepositoryImpl): ForecastRepository
}