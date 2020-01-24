package com.sukie2.android.currencyconverter.view.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sukie2.android.currencyconverter.R
import com.sukie2.android.currencyconverter.di.ConverterKoinComponent
import com.sukie2.android.currencyconverter.di.ConverterKoinContext
import com.sukie2.android.currencyconverter.view.adapters.CurrencyConverterAdapter
import com.sukie2.android.currencyconverter.view.adapters.OnCurrencyValueChangeListener
import com.sukie2.android.currencyconverter.viewmodel.ConverterViewModel
import kotlinx.android.synthetic.main.converter_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConverterFragment : Fragment() {

    private val viewModel by viewModel<ConverterViewModel>()
    private lateinit var adapter: CurrencyConverterAdapter

    companion object {
        fun newInstance() = ConverterFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.converter_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getCurrencyRates()

        adapter = CurrencyConverterAdapter(object : OnCurrencyValueChangeListener {
            override fun onValueChanged(symbol: String, amount: Float) {
                viewModel.getCurrencyRates()
            }
        })


        viewModel.currencyLiveData.observe(viewLifecycleOwner, Observer { event ->
            adapter.updateRates(event)
        })

        initView()
    }

    /**
     * Setup view
     */
    private fun initView() {
        recyclerViewConverter.setHasFixedSize(true)
        recyclerViewConverter.layoutManager = LinearLayoutManager(context)
        recyclerViewConverter.adapter = adapter
    }

}
