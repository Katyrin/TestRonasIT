package com.katyrin.testronasit.model.network

import com.katyrin.testronasit.BuildConfig
import com.katyrin.testronasit.model.storage.Storage
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class BaseInterceptor(private val storage: Storage) : Interceptor {

    private var responseCode = 0

    @Throws(IOException::class)
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
        proceed(request).also { response -> responseCode = response.code }
    }

    fun getResponseCode(): ServerResponseStatusCode =
        when (responseCode / 100) {
            1 -> ServerResponseStatusCode.INFO
            2 -> ServerResponseStatusCode.SUCCESS
            3 -> ServerResponseStatusCode.REDIRECTION
            4 -> ServerResponseStatusCode.CLIENT_ERROR
            5 -> ServerResponseStatusCode.SERVER_ERROR
            else -> ServerResponseStatusCode.UNDEFINED_ERROR
        }

    enum class ServerResponseStatusCode {
        INFO,
        SUCCESS,
        REDIRECTION,
        CLIENT_ERROR,
        SERVER_ERROR,
        UNDEFINED_ERROR
    }

    private companion object {
        const val QUERY_PARAMETER_APPID = "appid"
        const val QUERY_PARAMETER_UNITS = "units"
        const val QUERY_PARAMETER_LANG = "lang"
    }
}