package com.sukie2.android.currencyconverter

import android.app.Application
import com.sukie2.android.currencyconverter.di.converterKoinModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ConverterApplication: Application(){
    override fun onCreate(){
        super.onCreate()
        // start Koin!
        startKoin {
            androidContext(this@ConverterApplication)
            modules(converterKoinModule)
        }
    }
}