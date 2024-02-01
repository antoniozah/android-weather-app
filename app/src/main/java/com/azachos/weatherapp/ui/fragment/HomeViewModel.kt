package com.azachos.weatherapp.ui.fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.azachos.weatherapp.datapersistence.ErrorMapper
import com.azachos.weatherapp.datapersistence.dao.ForecastDao
import com.azachos.weatherapp.datapersistence.entities.ForecastDataEntity
import com.azachos.weatherapp.datapersistence.forecast.ForecastRepository
import com.azachos.weatherapp.model.forecast.ForecastResponse
import com.azachos.weatherapp.model.forecast.dto.ForecastDTO
import com.azachos.weatherapp.ui.BaseViewModel
import com.azachos.weatherapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val forecastRepository: ForecastRepository,
    private val errorMapper: ErrorMapper,
    private val forecastDao: ForecastDao
) : BaseViewModel() {
    private val _forecastData: MutableLiveData<ForecastDataEntity> = MutableLiveData()
    val forecastData: LiveData<ForecastDataEntity> get() = _forecastData

    private var _forecastLocationData: MutableLiveData<ForecastResponse?> = MutableLiveData()
    var forecastLocationData: LiveData<ForecastResponse?> = _forecastLocationData

    private var _forecastDays: MutableLiveData<Int> = MutableLiveData(3)
    val forecastDays: LiveData<Int> = _forecastDays

    private var _locationInputField: MutableLiveData<String> = MutableLiveData()
    val locationInputField: LiveData<String> = _locationInputField

    private var _errorMessage: MutableLiveData<String?> = MutableLiveData()
    val errorMessage: LiveData<String?> = _errorMessage

    fun changeLocationInputField(value: String) {
        _locationInputField.value = value
    }

    init {
        getLocalData()
    }

    fun getForecastData() = viewModelScope.launch {
        if (locationInputField.value?.isNotEmpty() == true) {
            forecastDays.value?.let { forecastDaysNumber ->
                forecastRepository.getLocationForecast(
                    ForecastDTO(locationInputField.value.toString(), forecastDaysNumber)
                ).collect { forecastResponse ->
                    when (forecastResponse) {
                        is Resource.Success -> {
                            getLocalData()
                            Log.d("WEATHER_APP", "RETURN DATA VIEWMODEL ${forecastResponse.data}")
//                            _forecastLocationData.postValue()
                            Log.d("WEATHER_APP", "SUCCESS: $forecastResponse")
                            setLoading(false)
                            setHasError(false)
                        }

                        is Resource.Error -> {
                            _errorMessage.postValue(forecastResponse.message?.let {
                                errorMapper.mapErrorToMessage(it)
//
                            })
                            forecastResponse.hasNetwork?.let {
                                setHasNetwork(it)
                            }
                            setLoading(false)
                            setHasError(true);
                        }

                        is Resource.Loading -> {
                            setLoading(true)
                        }
                    }
                }
            }
        }
    }

    fun getLocalData() {
        viewModelScope.launch {
            // Retrieve the forecast data and update the LiveData.
            Log.d("WEATHER_APP", "LOCAL DATA: ${forecastDao.getForecastData()}")
            _forecastData.value = forecastDao.getForecastData()
        }
    }


}