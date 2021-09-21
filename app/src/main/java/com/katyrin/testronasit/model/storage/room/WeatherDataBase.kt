package com.katyrin.testronasit.model.storage.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.katyrin.testronasit.model.data.WeatherEntity

@Database(entities = [WeatherEntity::class], version = 1, exportSchema = false)
abstract class WeatherDataBase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDAO
}