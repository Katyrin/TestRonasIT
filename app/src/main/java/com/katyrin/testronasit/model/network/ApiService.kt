package com.katyrin.testronasit.model.network

import com.katyrin.testronasit.model.data.WeatherRequest
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("weather?exclude=current,minutely,hourly,alerts")
    suspend fun getWeatherByCoordinate(
        @Query("lat") lat: Float,
        @Query("lon") lon: Float,
        @Query("units") unit: String,
        @Query("lang") lang: String,
        @Query("appid") key: String
    ): WeatherRequest
}