package com.katyrin.testronasit.model.network

import com.katyrin.testronasit.model.data.WeatherRequest
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("weather?exclude=current,minutely,hourly,alerts")
    suspend fun getWeatherByCoordinate(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): WeatherRequest

    @GET("weather?exclude=current,minutely,hourly,alerts")
    suspend fun getWeatherByCity(
        @Query("q") city: String
    ): WeatherRequest
}