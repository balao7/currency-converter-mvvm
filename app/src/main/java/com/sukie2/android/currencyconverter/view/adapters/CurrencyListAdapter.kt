package com.sukie2.android.currencyconverter.view.adapters

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sukie2.android.currencyconverter.R
import com.sukie2.android.currencyconverter.extentions.format
import com.sukie2.android.currencyconverter.model.Currency
import com.sukie2.android.currencyconverter.utility.getCurrencyFlagResId
import com.sukie2.android.currencyconverter.utility.getCurrencyNameResId
import kotlinx.android.synthetic.main.item_converter.view.*
import java.util.HashMap

class CurrencyConverterAdapter(private val onAmountChangedListener: OnCurrencyValueChangeListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val currencyPos = ArrayList<String>()
    private val currencyRate = HashMap<String, Currency>()

    private var amount = 1.0F

    /**
     * Update the rate of each currency
     */
    fun updateRates(rates: ArrayList<Currency>) {
        if (currencyPos.isEmpty()) {
            currencyPos.addAll(rates.map { it.name })
        }

        for (rate in rates) {
            currencyRate[rate.name] = rate
        }

        notifyItemRangeChanged(0, currencyPos.size - 1, amount)
    }

    override fun getItemCount(): Int {
        return currencyRate.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as RateViewHolder).bind(currencyRate.get(currencyPos[position])!!)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: List<Any>) {
        if (payloads.isNotEmpty()) {
            (holder as RateViewHolder).bind(currencyRate[currencyPos[position]]!!)
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RateViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_converter, parent, false))
    }

    /**
     * Viewholder for the currency
     */
    inner class RateViewHolder(itemView: View) : RecyclerView.ViewHolder(
        itemView) {

        var icCurrencyFlag: ImageView = itemView.icCurrencyFlag
        var lblCurrencySymbol: TextView = itemView.lblCurrencySymbol
        var lblCurrencyName: TextView = itemView.lblCurrencyName
        var txtCurrencyAmount: EditText = itemView.txtCurrencyAmount
        var symbol: String = ""


        /**
         * Change the view's values
         */
        fun bind(currency: Currency) {

            if (symbol != currency.name) {
                initView(currency)
                this.symbol = currency.name
            }

            // If the EditText holds the focus, we don't change the value
            if (!txtCurrencyAmount.isFocused) {
                txtCurrencyAmount.setText((currency.rate * amount).format())
            }
        }

        /**
         * Setup the view
         */
        private fun initView(currency: Currency) {
            val symbol = currency.name.toLowerCase()
            val nameId = getCurrencyNameResId(itemView.context, symbol)
            val flagId = getCurrencyFlagResId(itemView.context, symbol)

            lblCurrencySymbol.text = currency.name
            lblCurrencyName.text = itemView.context.getString(nameId)
            icCurrencyFlag.setImageResource(flagId)

            txtCurrencyAmount.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                //If view lost focus, we do nothing
                if (!hasFocus) {
                    return@OnFocusChangeListener
                }

                //If view is already on top, we do nothing, otherwise...
                layoutPosition.takeIf { it > 0 }?.also { currentPosition ->

                    //We move it from its current position
                    currencyPos.removeAt(currentPosition).also {
                        //And we add it to the top
                        currencyPos.add(0, it)
                    }

                    //We notify the recyclerview the view moved to position 0
                    notifyItemMoved(currentPosition, 0)

                }
            }

            txtCurrencyAmount.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable?) {

                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if(s.toString() != null && s.toString().isNotEmpty()) {
                        if (txtCurrencyAmount.isFocused) {
                            onAmountChangedListener.onValueChanged(symbol, s.toString().toFloat())
                        }
                    }

                }

            })
        }
    }
}