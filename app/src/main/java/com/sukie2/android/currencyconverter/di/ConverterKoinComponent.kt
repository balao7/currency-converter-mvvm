package com.sukie2.android.currencyconverter.di

import org.koin.core.Koin
import org.koin.core.KoinComponent

interface ConverterKoinComponent : KoinComponent {
    override fun getKoin(): Koin = ConverterKoinContext.koinApp.koin
}