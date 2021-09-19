package com.katyrin.testronasit.model.datasource

import com.katyrin.testronasit.model.data.WeatherRequest
import com.katyrin.testronasit.model.network.ApiService

class RemoteDataSourceImpl(private val apiService: ApiService) : RemoteDataSource {

    override suspend fun getWeatherByCoordinate(
        lat: Float,
        lon: Float,
        unit: String,
        lang: String,
        key: String
    ): WeatherRequest = apiService.getWeatherByCoordinate(lat, lon, unit, lang, key)
}