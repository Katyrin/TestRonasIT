package com.katyrin.testronasit.model.datasource

import com.katyrin.testronasit.model.data.WeatherDTO

interface LocalDataSource {
    suspend fun getWeather(): WeatherDTO
    suspend fun getWeatherByCity(city: String): WeatherDTO?
    suspend fun putWeather(weatherDTO: WeatherDTO)
}