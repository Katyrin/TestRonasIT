package com.katyrin.testronasit.di

import com.google.gson.GsonBuilder
import com.katyrin.testronasit.model.network.ApiService
import com.katyrin.testronasit.model.network.BaseInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

val network = module {
    single<Interceptor> { BaseInterceptor(storage = get()) }
    factory { provideClient(interceptor = get()) }
    factory { provideApi(okHttpClient = get()) }
}

private fun provideClient(interceptor: Interceptor): OkHttpClient =
    OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

private fun provideApi(okHttpClient: OkHttpClient): ApiService =
    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .client(okHttpClient)
        .build()
        .create(ApiService::class.java)