package com.sukie2.android.currencyconverter.ui.views

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sukie2.android.currencyconverter.R
import com.sukie2.android.currencyconverter.ui.viewmodels.ConverterViewModel

class ConverterFragment : Fragment() {

    companion object {
        fun newInstance() = ConverterFragment()
    }

    private lateinit var viewModel: ConverterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.converter_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ConverterViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
