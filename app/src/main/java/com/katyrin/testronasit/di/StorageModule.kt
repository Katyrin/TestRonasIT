package com.katyrin.testronasit.di

import androidx.room.Room
import com.katyrin.testronasit.model.storage.Storage
import com.katyrin.testronasit.model.storage.StorageImpl
import com.katyrin.testronasit.model.storage.room.WeatherDataBase
import org.koin.dsl.module

val storage = module {
    single<Storage> { StorageImpl(context = get()) }
}

val database = module {
    single { Room.databaseBuilder(get(), WeatherDataBase::class.java, "WeatherDB").build() }
    single { get<WeatherDataBase>().weatherDao() }
}