package com.katyrin.testronasit.model.storage.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.katyrin.testronasit.model.data.WeatherEntity

@Dao
interface WeatherDAO {

    @Query("SELECT * FROM WeatherEntity ORDER BY id DESC LIMIT 1")
    suspend fun getWeather(): WeatherEntity

    @Query("SELECT * FROM WeatherEntity WHERE city LIKE :city")
    suspend fun getWeatherByCity(city: String): WeatherEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun putWeather(weather: WeatherEntity)
}