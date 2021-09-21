package com.katyrin.testronasit.model.repository

import com.katyrin.testronasit.model.data.WeatherDTO
import com.katyrin.testronasit.model.datasource.LocalDataSource
import com.katyrin.testronasit.model.datasource.RemoteDataSource
import com.katyrin.testronasit.model.storage.Storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val storage: Storage,
    private val localDataSource: LocalDataSource
) : Repository {

    override suspend fun getWeatherByCoordinate(lat: Double, lon: Double): WeatherDTO =
        withContext(Dispatchers.IO) {
            remoteDataSource.getWeatherByCoordinate(lat, lon)
                .also { weatherDTO -> localDataSource.putWeather(weatherDTO) }
        }

    override suspend fun getWeatherByCity(city: String): WeatherDTO = withContext(Dispatchers.IO) {
        remoteDataSource.getWeatherByCity(city)
            .also { weatherDTO -> localDataSource.putWeather(weatherDTO) }
    }

    override suspend fun getWeather(): WeatherDTO? = withContext(Dispatchers.IO) {
        val city = localDataSource.getWeather().city
        if (city == "") null else getWeatherByCity(city)
    }

    override suspend fun getWeatherByCityLocal(city: String): WeatherDTO? =
        withContext(Dispatchers.IO) { localDataSource.getWeatherByCity(city) }

    override fun getMeasure(): Boolean = storage.isMetric()

    override fun setMeasure(isMetric: Boolean): Unit = storage.setMeasure(isMetric)
}