package com.katyrin.testronasit.model.repository

import com.katyrin.testronasit.model.data.WeatherDTO

interface Repository {
    suspend fun getWeatherByCoordinate(lat: Double, lon: Double): WeatherDTO
    suspend fun getWeatherByCity(city: String): WeatherDTO
    fun getMeasure(): Boolean
    fun setMeasure(isMetric: Boolean)
}