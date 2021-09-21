package com.katyrin.testronasit

import android.app.Application
import com.katyrin.testronasit.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(listOf(application, mainModule, network, storage, database))
        }
    }
}