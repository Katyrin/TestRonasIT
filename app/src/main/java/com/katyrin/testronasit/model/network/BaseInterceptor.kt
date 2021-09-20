package com.katyrin.testronasit.model.network

import com.katyrin.testronasit.BuildConfig
import com.katyrin.testronasit.model.storage.Storage
import okhttp3.Interceptor
import okhttp3.Response

class BaseInterceptor(private val storage: Storage) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        val url = request().url
            .newBuilder()
            .addQueryParameter(QUERY_PARAMETER_UNITS, storage.getMeasure())
            .addQueryParameter(QUERY_PARAMETER_LANG, storage.getLanguage())
            .addQueryParameter(QUERY_PARAMETER_APPID, BuildConfig.WEATHER_API_KEY)
            .build()
        val request = request()
            .newBuilder()
            .url(url)
            .build()
        proceed(request)
    }

    private companion object {
        const val QUERY_PARAMETER_APPID = "appid"
        const val QUERY_PARAMETER_UNITS = "units"
        const val QUERY_PARAMETER_LANG = "lang"
    }
}