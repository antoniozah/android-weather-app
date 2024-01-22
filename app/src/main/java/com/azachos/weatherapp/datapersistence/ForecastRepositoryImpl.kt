package com.azachos.weatherapp.datapersistence

import com.azachos.weatherapp.api.RetrofitInstance
import com.azachos.weatherapp.model.ForecastResponse
import com.azachos.weatherapp.utils.Constants.Companion.HTTP.GENERIC_ERROR_MESSAGE
import com.azachos.weatherapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ForecastRepositoryImpl @Inject constructor() : ForecastRepository {
    override suspend fun getLocationForecast(
        location: String,
        days: Int
    ): Flow<Resource<ForecastResponse>> = flow{
        try {
            emit(Resource.Loading())
            val response = RetrofitInstance.api.getForecast(location)
            when (response.isSuccessful) {
                true -> {
                    emit(Resource.Success(response.body()!!))
                }
                false -> {
                    emit(Resource.Error(GENERIC_ERROR_MESSAGE, null))
                }
            }
        } catch (error: Exception) {
            emit(Resource.Error(error.localizedMessage ?: GENERIC_ERROR_MESSAGE, null))
        }
    }
}