package com.sukie2.android.currencyconverter.di

import com.sukie2.android.currencyconverter.networking.ConverterRepository
import com.sukie2.android.currencyconverter.networking.ServiceApiHelper
import com.sukie2.android.currencyconverter.view.viewmodel.ConverterViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val converterKoinModule = module {
    single { ServiceApiHelper().createServiceRetrofit() }
    single { ConverterRepository(get(), androidContext()) }
    viewModel { ConverterViewModel() }
}