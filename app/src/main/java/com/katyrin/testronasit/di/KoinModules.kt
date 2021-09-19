package com.katyrin.testronasit.di

import com.katyrin.testronasit.model.datasource.RemoteDataSource
import com.katyrin.testronasit.model.datasource.RemoteDataSourceImpl
import com.katyrin.testronasit.model.mapping.WeatherMapping
import com.katyrin.testronasit.model.mapping.WeatherMappingImpl
import com.katyrin.testronasit.model.repository.Repository
import com.katyrin.testronasit.model.repository.RepositoryImpl
import com.katyrin.testronasit.viewmodel.MainViewModel
import org.koin.dsl.module

val application = module {
    single<RemoteDataSource> { RemoteDataSourceImpl(apiService = get(), weatherMapping = get()) }
    single<WeatherMapping> { WeatherMappingImpl(context = get(), storage = get()) }
    single<Repository> { RepositoryImpl(remoteDataSource = get(), storage = get()) }
}

val mainModule = module {
    factory { MainViewModel(repository = get()) }
}