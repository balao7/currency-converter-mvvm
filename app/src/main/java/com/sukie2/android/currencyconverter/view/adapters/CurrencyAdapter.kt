package com.sukie2.android.currencyconverter.view.adapters

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.recyclerview.widget.RecyclerView
import com.sukie2.android.currencyconverter.R
import com.sukie2.android.currencyconverter.model.RVCurrency
import com.sukie2.android.currencyconverter.utility.getCurrencyFlagResId
import com.sukie2.android.currencyconverter.utility.getCurrencyNameResId
import kotlinx.android.synthetic.main.item_converter.view.*
import java.util.*

class CurrencyAdapter(private val onAmountChangedListener: BaseValueChangeListener) :
    RecyclerView.Adapter<CurrencyAdapter.ViewHolder>() {
    lateinit var context: Context
    var itemPositionList: MutableList<String> = arrayListOf()
    var itemsMap = HashMap<String, RVCurrency>()

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty()) {
            when (payloads[0]) {
                3.0 -> {
                    holder.etAmount.setText(itemsMap[itemPositionList[position]]?.rate.toString())
                }
            }
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_converter,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return itemPositionList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val symbol = itemsMap[itemPositionList[position]]?.name?.toLowerCase(Locale.UK) ?: ""
        val nameId = getCurrencyNameResId(context, symbol)
        val flagId = getCurrencyFlagResId(context, symbol)

        holder.lblCurrencySymbol.text = itemsMap[itemPositionList[position]]?.name
        holder.lblCurrencyName.text = context.getString(nameId)
        holder.icCurrencyFlag.setImageResource(flagId)
        holder.etAmount.setText(itemsMap[itemPositionList[position]]?.rate.toString())
        holder.etAmount.addTextChangedListener(holder.textWatcher)
        holder.etAmount.onFocusChangeListener = holder.focusChangeListener
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val lblCurrencySymbol: TextView = view.lblCurrencySymbol
        val icCurrencyFlag: ImageView = view.icCurrencyFlag
        val lblCurrencyName: TextView = view.lblCurrencyName
        val etAmount: AppCompatEditText = view.txtCurrencyAmount

        val textWatcher: TextWatcher = object : TextWatcher {
            override fun afterTextChanged(text: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (etAmount.isFocused) {
                    if (charSequence.toString().isNotEmpty()) {
                        onAmountChangedListener.onBaseAmountChanged(charSequence.toString().toFloat())
                    }else{
                        onAmountChangedListener.onBaseAmountChanged(0f)
                    }
                }
            }
        }

        val focusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                return@OnFocusChangeListener
            }

            layoutPosition.takeIf { it > 0 }?.also { currentPosition ->
                itemPositionList.removeAt(currentPosition).also { itemPositionList.add(0, it) }
                notifyItemMoved(currentPosition, 0)
                onAmountChangedListener.onBaseCurrencyChanged(
                    itemsMap[itemPositionList[0]]?.name ?: "",
                    itemsMap[itemPositionList[0]]?.rate ?: 1f
                )
            }
        }
    }
}

