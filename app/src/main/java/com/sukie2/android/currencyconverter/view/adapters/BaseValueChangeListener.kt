package com.sukie2.android.currencyconverter.view.adapters

import com.sukie2.android.currencyconverter.model.RVCurrency

/**
 * Implement this to get notified when the user changes value of the currency.
 *
 */
interface BaseValueChangeListener {

    /**
     * Function gets called when the user changed the base amount.
     */
    fun onBaseAmountChanged(baseAmount: Float)

    /**
     * Function gets called when the user changed the base currency.
     */
    fun onBaseCurrencyChanged(baseCurrency: String, baseAmount: Float)
}