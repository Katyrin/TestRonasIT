package com.katyrin.testronasit.model.datasource

import com.katyrin.testronasit.model.data.WeatherDTO
import com.katyrin.testronasit.model.mapping.WeatherMapping
import com.katyrin.testronasit.model.network.ApiService

class RemoteDataSourceImpl(
    private val apiService: ApiService,
    private val weatherMapping: WeatherMapping
) : RemoteDataSource {

    override suspend fun getWeatherByCoordinate(lat: Double, lon: Double): WeatherDTO =
        weatherMapping.mapWeatherRequestToWeatherDTO(apiService.getWeatherByCoordinate(lat, lon))

    override suspend fun getWeatherByCity(city: String): WeatherDTO =
        weatherMapping.mapWeatherRequestToWeatherDTO(apiService.getWeatherByCity(city))
}