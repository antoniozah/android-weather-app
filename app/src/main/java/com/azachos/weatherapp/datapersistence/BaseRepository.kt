package com.azachos.weatherapp.datapersistence

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.azachos.weatherapp.MyApp.Companion.applicationContent
import com.azachos.weatherapp.model.forecast.ErrorHttpResponse
import com.azachos.weatherapp.utils.Constants.ERROR.GENERIC_ERROR_MESSAGE
import com.azachos.weatherapp.utils.Constants.ERROR.NO_CONNECTION_ERROR_MESSAGE
import com.azachos.weatherapp.utils.Resource
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.IOException

abstract class BaseRepository() {

    protected suspend fun <T> executeRequest(
        apiCall: suspend () -> HttpResponse,
        httpResponse: suspend (HttpResponse) -> T,
        hasNetworkType: Boolean?
    ): Flow<Resource<T>> = flow {
        try {
        //TODO:: To add network type handing logic when we will need it
            Log.d("WEATHER_APP", "Network Type: ${getNetworkType()}")
            if (isNetworkAvailable()) {
                coroutineScope {
                    emit(Resource.Loading())
                    val response = withContext(Dispatchers.IO) {
                        apiCall.invoke()
                    }

                    if (response.status.isSuccess()) {
                        val result = httpResponse.invoke(response)
                        emit(Resource.Success(result))
                    } else {
                        emit(
                            Resource.Error(
                                response.body<ErrorHttpResponse>().error?.message
                                    ?: GENERIC_ERROR_MESSAGE, null, true
                            )
                        )
                    }
                }
            } else {
                emit(Resource.Error(NO_CONNECTION_ERROR_MESSAGE, null, false))
            }
        } catch (error: IOException) {
            emit(Resource.Error(error.localizedMessage ?: GENERIC_ERROR_MESSAGE, null, null))
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val networkCapabilities = getNetworkCapabilities()
        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            ?: false
    }

    private fun getNetworkType(): String {
        val networkCapabilities = getNetworkCapabilities()
        return when {
            networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true -> "WIFI"
            networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true -> "CELLULAR"
            else -> "UNKNOWN"
        }
    }

    private fun getNetworkCapabilities(): NetworkCapabilities? {
        val connectivityManager =
            applicationContent.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    }

}