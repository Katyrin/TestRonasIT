package com.katyrin.testronasit.model.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["city"], unique = true)])
data class WeatherEntity(
    val city: String = "",
    val temperature: String = "",
    val description: String = "",
    val wind: String = "",
    val pressure: String = "",
    val humidity: String = "",
    val rain: String = "",
    val icon: String = "",
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)