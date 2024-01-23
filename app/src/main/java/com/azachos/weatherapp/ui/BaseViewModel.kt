package com.azachos.weatherapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel: ViewModel() {
    private var _loadingData: MutableLiveData<Boolean> = MutableLiveData()
    val loadingData: LiveData<Boolean> = _loadingData

    private var _hasError: MutableLiveData<Boolean> = MutableLiveData(false)
    val hasError: LiveData<Boolean> = _hasError

    private var _hasNetwork: MutableLiveData<Boolean?> = MutableLiveData()
    val hasNetwork = _hasNetwork

    fun setLoading(loadingValue: Boolean) {
        _loadingData.value = loadingValue
    }

    fun setHasError(errorValue: Boolean) {
        _hasError.value = errorValue
    }

    fun setHasNetwork(value: Boolean) {
        _hasNetwork.value = value
    }
}