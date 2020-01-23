package com.sukie2.android.currencyconverter.newtworking

import android.content.Context
import android.widget.Toast
import com.sukie2.android.currencyconverter.model.CurrencyResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConverterRepository(private val serviceApi: ServiceApi, val context: Context) {

    //Using Kotlin higher order functions to simplify the class
    fun getTrafficDateFromService(baseCurrency: String, cbOnSuccess: (CurrencyResponse?) -> Unit) {
        serviceApi.getLatestRates(baseCurrency).enqueue(object: Callback<CurrencyResponse> {

            override fun onFailure(call: Call<CurrencyResponse>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<CurrencyResponse>, response: Response<CurrencyResponse>) {
                cbOnSuccess(response.body())
            }

        })

    }
}