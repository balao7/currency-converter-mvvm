package com.sukie2.android.currencyconverter.extentions

import java.util.*


/**
 * Format the float to string
 * Locale safe
 */
fun Float.format() : String = String.format(Locale.getDefault(), "%.2f", this)

/**
 * Returns empty ("") if the string object is null
 */
fun String?.filterNullForEmpty(): String{
    if(this == null)
        return ""
    return this
}