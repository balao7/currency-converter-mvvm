package com.sukie2.android.currencyconverter.di

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.dsl.koinApplication

object ConverterKoinContext {

    lateinit var koinApp : KoinApplication

    fun init(context: Context?) {
        koinApp = koinApplication {
            if(context != null)
                androidContext(context)
            modules(listOf(
                converterKoinModule))
        }
    }
}