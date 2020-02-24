package com.sukie2.android.currencyconverter.extentions

/**
 * Returns empty ("") if the string object is null
 */
fun String?.filterNullForEmpty(): String {
    if (this == null)
        return ""
    return this
}