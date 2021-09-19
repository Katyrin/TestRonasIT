package com.katyrin.testronasit.model.repository

import com.katyrin.testronasit.model.data.WeatherRequest

interface Repository {

    suspend fun getWeatherByCoordinate(
        lat: Float,
        lon: Float,
        unit: String,
        lang: String,
        key: String
    ): WeatherRequest
}