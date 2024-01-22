package com.azachos.weatherapp.ui.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.azachos.weatherapp.datapersistence.ForecastRepository
import com.azachos.weatherapp.model.ForecastResponse
import com.azachos.weatherapp.ui.BaseViewModel
import com.azachos.weatherapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel  @Inject constructor(
    private val forecastRepository: ForecastRepository
) : BaseViewModel() {
    private var _forecastLocationData: MutableLiveData<ForecastResponse?> = MutableLiveData()
    var forecastLocationData: LiveData<ForecastResponse?> = _forecastLocationData

    private var _forecastDays: MutableLiveData<Int> = MutableLiveData(3)
    val forecastDays: LiveData<Int> = _forecastDays

    private var _errorMessage: MutableLiveData<String?> = MutableLiveData()
    val errorMessage: LiveData<String?> = _errorMessage

    fun getForecastData(location: String) = viewModelScope.launch {
        forecastDays.value?.let {
            forecastRepository.getLocationForecast(location, 3).collect { forecastResponse ->
                when(forecastResponse) {
                    is Resource.Success -> {
                        _forecastLocationData.value = forecastResponse.data
                        setLoading(false)
                    }
                    is Resource.Error -> {
                        _errorMessage.value = forecastResponse.message
                        setLoading(false)
                    }
                    is Resource.Loading -> {
                        setLoading(true)
                    }
                }
            }
        }
    }


}