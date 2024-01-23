package com.azachos.weatherapp.ui.fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.azachos.weatherapp.datapersistence.ErrorMapper
import com.azachos.weatherapp.datapersistence.forecast.ForecastRepository
import com.azachos.weatherapp.model.ForecastResponse
import com.azachos.weatherapp.model.forecast.dto.ForecastDTO
import com.azachos.weatherapp.ui.BaseViewModel
import com.azachos.weatherapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.logging.ErrorManager
import javax.inject.Inject

@HiltViewModel
class HomeViewModel  @Inject constructor(
    private val forecastRepository: ForecastRepository,
    private val errorMapper: ErrorMapper
) : BaseViewModel() {
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

    fun getForecastData() = viewModelScope.launch {
        if (locationInputField.value?.isNotEmpty() == true) {
            forecastDays.value?.let { forecastDaysNumber ->
                forecastRepository.getLocationForecast(
                    ForecastDTO(locationInputField.value.toString(), forecastDaysNumber)).collect { forecastResponse ->
                    when(forecastResponse) {
                        is Resource.Success -> {
                            _forecastLocationData.postValue(forecastResponse.data)
                            setLoading(false)
                            Log.d("WEATHER_APP", "SUCCESS: $forecastResponse")
                            setLoading(false)
                            setHasError(false);
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


}