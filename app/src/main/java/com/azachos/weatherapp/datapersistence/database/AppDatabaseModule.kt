package com.azachos.weatherapp.datapersistence.database

import android.content.Context
import androidx.room.Room
import com.azachos.weatherapp.datapersistence.typeconverters.ForecastTypeConverter
import com.azachos.weatherapp.utils.Constants.DATABASE.APPLICATION_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppDatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabaseModule(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            APPLICATION_DATABASE_NAME
        )
            .addTypeConverter(ForecastTypeConverter())
            .build()
}