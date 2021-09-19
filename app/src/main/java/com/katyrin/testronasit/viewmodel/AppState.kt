package com.katyrin.testronasit.viewmodel

import com.katyrin.testronasit.model.data.WeatherRequest

sealed class AppState {
    data class Success(val weatherRequest: WeatherRequest) : AppState()
    data class Error(val message: String?) : AppState()
    object Loading : AppState()
}