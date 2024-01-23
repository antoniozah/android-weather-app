package com.azachos.weatherapp.datapersistence

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ErrorMapperModule {

    @Provides
    @Singleton
    fun provideErrorMapper() : ErrorMapper {
        return ErrorMapperImpl()
    }
}