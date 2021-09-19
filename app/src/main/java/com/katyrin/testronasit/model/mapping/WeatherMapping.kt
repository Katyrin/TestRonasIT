package com.katyrin.testronasit.model.mapping

import com.katyrin.testronasit.model.data.WeatherDTO
import com.katyrin.testronasit.model.data.WeatherRequest

interface WeatherMapping {
    fun mapWeatherRequestToWeatherDTO(weatherRequest: WeatherRequest): WeatherDTO
}