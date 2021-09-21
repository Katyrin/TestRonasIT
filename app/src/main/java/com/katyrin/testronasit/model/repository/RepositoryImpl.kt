package com.katyrin.testronasit.model.repository

import com.katyrin.testronasit.model.data.WeatherDTO
import com.katyrin.testronasit.model.datasource.RemoteDataSource
import com.katyrin.testronasit.model.storage.Storage

class RepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val storage: Storage
) : Repository {

    override suspend fun getWeatherByCoordinate(lat: Double, lon: Double): WeatherDTO =
        remoteDataSource.getWeatherByCoordinate(lat, lon)

    override suspend fun getWeatherByCity(city: String): WeatherDTO =
        remoteDataSource.getWeatherByCity(city)

    override fun getMeasure(): Boolean = storage.isMetric()

    override fun setMeasure(isMetric: Boolean): Unit = storage.setMeasure(isMetric)
}