package com.sukie2.android.currencyconverter.model

import com.google.gson.annotations.SerializedName

data class CurrencyResponse(
    @SerializedName("base") val base : String,
    @SerializedName("dates") val dates : String,
    @SerializedName("rates") val rates : Map<String, Float>
)