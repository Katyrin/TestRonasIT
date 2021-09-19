package com.katyrin.testronasit.model.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import kotlin.jvm.Throws

class BaseInterceptor : Interceptor {

    private var responseCode = 0

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response =
        chain.proceed(chain.request()).also { response ->
            responseCode = response.code
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

    companion object {
        val interceptor: Interceptor
            get() = BaseInterceptor()
    }
}