package com.sukie2.android.currencyconverter.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sukie2.android.currencyconverter.extentions.filterNullForEmpty
import com.sukie2.android.currencyconverter.data.model.RVCurrency
import com.sukie2.android.currencyconverter.data.model.CurrencyResponse
import com.sukie2.android.currencyconverter.networking.ConverterRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject


class ConverterViewModel : ViewModel(), KoinComponent {
    private val repository: ConverterRepository by inject()
    val currencyLiveData = MutableLiveData<List<RVCurrency>>()
    var baseAmount: Float = 1.0f
    var baseCurrency = "EUR"

    /**
     * This appends the base currency record to the top of the currency list returned from remote service.
     */
    private fun getConfiguredList(response: CurrencyResponse?): List<RVCurrency> {
        val list = mutableListOf<RVCurrency>()
        response?.rates?.forEach { rate ->
            list.add(RVCurrency(rate.key, rate.value))
        }
        list.add(0, RVCurrency(response?.base.filterNullForEmpty(), baseAmount))
        return list
    }

    /**
     * This fires a service call to get the latest rates every one second.
     */
    fun starDownloadingRates() {
        viewModelScope.launch {
            while (isActive) {
                delay(1000)
                repository.getRatesFromServer(baseCurrency = baseCurrency, cbOnSuccess = {
                    currencyLiveData.value = getConfiguredList(it)
                })
            }
        }
    }
}
