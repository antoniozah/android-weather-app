package com.azachos.weatherapp.datapersistence

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.azachos.weatherapp.MyApp.Companion.applicationContent
import com.azachos.weatherapp.model.ErrorHttpResponse
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

    protected suspend inline fun <reified T> mapResponse(response: HttpResponse): T =
        withContext(Dispatchers.IO) {
            response.body<T>()
        }

    protected suspend fun <T> executeRequest(
        apiCall: suspend () -> HttpResponse,
        mapper: suspend (HttpResponse) -> T,
        hasNetworkType: Boolean?
    ): Flow<Resource<T>> = flow {
        try {
//            if(hasNetworkType) {
//                when(getNetworkType()) {
//                     "WIFI" -> {
//
//                    }
//                    "CELLULAR" -> {
//
//                    }
//                }
//            }
            if (isNetworkAvailable()) {
                coroutineScope {
                    emit(Resource.Loading())
                    val response = withContext(Dispatchers.IO) {
                        apiCall.invoke()
                    }

                    if (response.status.isSuccess()) {
                        val result = mapper.invoke(response)
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
        val connectivityManager =
            applicationContent.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            ?: false
    }

    private fun getNetworkType(): String {
        val connectivityManager =
            applicationContent.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

        return when {
            networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true -> "WIFI"
            networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true -> "CELLULAR"
            else -> "UNKNOWN"
        }
    }

}