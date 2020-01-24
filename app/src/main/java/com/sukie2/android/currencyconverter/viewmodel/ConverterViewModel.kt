package com.sukie2.android.currencyconverter.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sukie2.android.currencyconverter.model.Currency
import com.sukie2.android.currencyconverter.networking.ConverterRepository
import org.koin.core.KoinComponent
import org.koin.core.inject


class ConverterViewModel : ViewModel(), KoinComponent {
    private val repository: ConverterRepository by inject()
    val currencyLiveData = MutableLiveData<ArrayList<Currency>>()
    var camerasList: List<String> = listOf()

    //Using Kotlin higher order functions to simplify the class
    fun getCurrencyRates() {
        repository.getTrafficDateFromService("EUR", cbOnSuccess = {
            val list = mutableListOf<Currency>()
            it?.rates?.forEach { rate ->
                list.add(Currency(rate.key, rate.value))
            }
            currencyLiveData.value = list as ArrayList<Currency>
        })
    }


//    val trafficLiveData: MutableLiveData<List<TrafficData>> by lazy {
//        MutableLiveData<List<TrafficData>>()
//    }
}
