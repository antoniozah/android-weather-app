package com.azachos.weatherapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel: ViewModel() {
    private var _loadingData: MutableLiveData<Boolean> = MutableLiveData()
    val loadingData: LiveData<Boolean> = _loadingData

    fun setLoading(loadingValue: Boolean) {
        _loadingData.value = loadingValue
    }
}