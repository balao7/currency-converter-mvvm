package com.sukie2.android.currencyconverter.view.adapters

/**
 * Implement this to get notified when the user changes value of the currency.
 *
 */
interface OnCurrencyValueChangeListener {

    /**
     * Function gets called when the user changed the amount for a given currency.
     *
     */
    fun onValueChanged(symbol: String, amount: Float)
}