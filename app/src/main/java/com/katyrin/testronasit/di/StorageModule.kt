package com.katyrin.testronasit.di

import com.katyrin.testronasit.model.storage.Storage
import com.katyrin.testronasit.model.storage.StorageImpl
import org.koin.dsl.module

val storage = module {
    single<Storage> { StorageImpl(context = get()) }
}