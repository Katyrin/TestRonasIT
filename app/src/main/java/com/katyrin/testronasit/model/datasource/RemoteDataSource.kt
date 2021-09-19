package com.katyrin.testronasit.model.datasource

import com.katyrin.testronasit.model.data.WeatherDTO

interface RemoteDataSource {
    suspend fun getWeatherByCoordinate(lat: Double, lon: Double): WeatherDTO
}