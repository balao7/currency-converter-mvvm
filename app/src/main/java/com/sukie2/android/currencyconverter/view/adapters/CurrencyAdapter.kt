package com.sukie2.android.currencyconverter.view.adapters

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.recyclerview.widget.RecyclerView
import com.sukie2.android.currencyconverter.R
import com.sukie2.android.currencyconverter.extentions.filterNullForEmpty
import com.sukie2.android.currencyconverter.extentions.format
import com.sukie2.android.currencyconverter.data.model.RVCurrency
import com.sukie2.android.currencyconverter.utility.getCurrencyFlagResId
import com.sukie2.android.currencyconverter.utility.getCurrencyNameResId
import kotlinx.android.synthetic.main.item_converter.view.*
import java.util.*

const val LOAD_RATES = 1

class CurrencyAdapter(private val onAmountChangedListener: BaseValueChangeListener) :
    RecyclerView.Adapter<CurrencyAdapter.ViewHolder>() {
    private lateinit var context: Context
    var itemPositionList: MutableList<String> = arrayListOf()
    var itemsMap = HashMap<String, RVCurrency>()
    var baseAmount = 1.0f

    override fun getItemCount(): Int {
        return itemPositionList.size
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty()) {
            when (payloads[0]) {
                LOAD_RATES -> {
                    val rate = itemsMap[itemPositionList[position]]?.rate
                    holder.etAmount.setText(((rate?:0f) * baseAmount).format())
                }
            }
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val symbol = itemsMap[itemPositionList[position]]?.name?.toLowerCase(Locale.getDefault()) ?: ""
        val nameId = getCurrencyNameResId(context, symbol)
        val flagId = getCurrencyFlagResId(context, symbol)

        holder.lblCurrencySymbol.text = itemsMap[itemPositionList[position]]?.name
        holder.lblCurrencyName.text = context.getString(nameId)
        holder.icCurrencyFlag.setImageResource(flagId)
        val rate = itemsMap[itemPositionList[position]]?.rate
        holder.etAmount.setText(((rate?:0f) * baseAmount).format())
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
                    val newVal = charSequence.toString().isNotEmpty()
                    baseAmount = if (newVal) charSequence.toString().toFloat() else 0f
                    notifyItemRangeChanged(1, itemPositionList.lastIndex, LOAD_RATES)
                }
            }
        }

        val focusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                return@OnFocusChangeListener
            }

            layoutPosition.takeIf { it > 0 }?.also { currentPosition ->
                itemPositionList.removeAt(currentPosition).also { itemPositionList.add(0, it) }
                notifyItemMoved(currentPosition, 0)

                val tempCur = itemsMap[itemPositionList[0]]
                baseAmount = (view as EditText).text.toString().toFloat()
                onAmountChangedListener.onBaseCurrencyChanged(
                    tempCur?.name.filterNullForEmpty(), baseAmount)
            }
        }
    }
}

