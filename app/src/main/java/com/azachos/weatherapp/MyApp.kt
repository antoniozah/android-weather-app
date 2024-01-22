package com.azachos.weatherapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {
    companion object {
        var applicationContent: MyApp = MyApp()
    }

    init {
        applicationContent = this
    }
}