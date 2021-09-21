package com.katyrin.testronasit.viewmodel

import com.katyrin.testronasit.model.data.WeatherDTO

sealed class AppState {
    data class Success(val weather: WeatherDTO) : AppState()
    data class Error(val errorState: ErrorState) : AppState()
    object EmptyWeatherData: AppState()
    object Loading : AppState()
    object Metric : AppState()
    object Imperial : AppState()
}