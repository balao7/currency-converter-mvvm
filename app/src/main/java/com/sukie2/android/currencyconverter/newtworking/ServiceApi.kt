package com.sukie2.android.currencyconverter.newtworking

import com.sukie2.android.currencyconverter.model.CurrencyResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceApi {
    @GET("latest")
    fun getLatestRates(
        @Query("base") baseCurrency:String
    ): Call<CurrencyResponse>
}