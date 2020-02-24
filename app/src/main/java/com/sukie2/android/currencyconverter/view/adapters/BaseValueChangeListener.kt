package com.sukie2.android.currencyconverter.view.adapters

/**
 * Implement this to get notified when the user changes value of the currency.
 *
 */
interface BaseValueChangeListener {

    /**
     * Function gets called when the user changed the base currency.
     */
    fun onBaseCurrencyChanged(baseCurrency: String, baseAmount: Float)
}