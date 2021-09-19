package com.katyrin.testronasit.model.datasource

import com.katyrin.testronasit.model.data.WeatherRequest

interface RemoteDataSource {

    suspend fun getWeatherByCoordinate(
        lat: Float,
        lon: Float,
        unit: String,
        lang: String,
        key: String
    ): WeatherRequest
}