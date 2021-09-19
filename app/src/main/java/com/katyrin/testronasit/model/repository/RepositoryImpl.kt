package com.katyrin.testronasit.model.repository

import com.katyrin.testronasit.model.data.WeatherRequest
import com.katyrin.testronasit.model.datasource.RemoteDataSource

class RepositoryImpl(private val remoteDataSource: RemoteDataSource) : Repository {

    override suspend fun getWeatherByCoordinate(
        lat: Float,
        lon: Float,
        unit: String,
        lang: String,
        key: String
    ): WeatherRequest = remoteDataSource.getWeatherByCoordinate(lat, lon, unit, lang, key)
}