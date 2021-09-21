package com.katyrin.testronasit.model.datasource

import com.katyrin.testronasit.model.data.WeatherDTO
import com.katyrin.testronasit.model.mapping.WeatherMapping
import com.katyrin.testronasit.model.storage.room.WeatherDAO

class LocalDataSourceImpl(
    private val weatherDAO: WeatherDAO,
    private val weatherMapping: WeatherMapping
) : LocalDataSource {
    override suspend fun getWeather(): WeatherDTO =
        weatherMapping.mapEntityToDTO(weatherDAO.getWeather())

    override suspend fun getWeatherByCity(city: String): WeatherDTO? {
        val weatherDTO = weatherDAO.getWeatherByCity(city)
        return if (weatherDTO == null) null else weatherMapping.mapEntityToDTO(weatherDTO)
    }

    override suspend fun putWeather(weatherDTO: WeatherDTO): Unit =
        weatherDAO.putWeather(weatherMapping.mapDTOToEntity(weatherDTO))
}