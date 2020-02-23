package com.sukie2.android.currencyconverter.utility

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * Returns the currency name resource id using its symbol.
 */
fun getCurrencyNameResId(context: Context, symbol: String) =
    context.resources.getIdentifier("currency_" + symbol + "_name", "string",
        context.packageName)

/**
 * Returns the currency flag resource id using its symbol.
 */
fun getCurrencyFlagResId(context: Context, symbol: String) = context.resources.getIdentifier(
    "ic_" + symbol + "_flag", "drawable", context.packageName)


fun isOnline(context: Context?): Boolean {
    val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
    return activeNetwork?.isConnectedOrConnecting == true
}