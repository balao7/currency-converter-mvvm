package com.sukie2.android.currencyconverter.viewmodel

import androidx.lifecycle.ViewModel
import com.sukie2.android.currencyconverter.newtworking.ConverterRepository
import org.koin.core.KoinComponent
import org.koin.core.inject


class ConverterViewModel : ViewModel(), KoinComponent {
    private val repository: ConverterRepository by inject()
    var camerasList: List<String> = listOf()

    //Using Kotlin higher order functions to simplify the class
    fun getTrafficDateFromService() {
        repository.getTrafficDateFromService("EUR", cbOnSuccess = {

            })
    }

//    val trafficLiveData: MutableLiveData<List<TrafficData>> by lazy {
//        MutableLiveData<List<TrafficData>>()
//    }
}
