package com.sukie2.android.currencyconverter.view.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sukie2.android.currencyconverter.R
import com.sukie2.android.currencyconverter.di.ConverterKoinComponent
import com.sukie2.android.currencyconverter.di.ConverterKoinContext
import com.sukie2.android.currencyconverter.viewmodel.ConverterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConverterFragment : Fragment() {

    private val viewModel by viewModel<ConverterViewModel>()

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
        viewModel.getTrafficDateFromService()
    }

}
