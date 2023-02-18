package com.training.countriesapp.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.training.countriesapp.R
import com.training.countriesapp.adapter.DetailsAdapter
import com.training.countriesapp.databinding.FragmentCountryDetailsBinding
import com.training.countriesapp.model.LoadingState
import com.training.countriesapp.viewmodel.CountryViewModel

class CountryDetailsFragment : Fragment(R.layout.fragment_country_details) {
    private val viewModel: CountryViewModel by viewModels()
    private lateinit var fragmentBinding: FragmentCountryDetailsBinding
    private var adapter = DetailsAdapter(mutableListOf())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentCountryDetailsBinding.bind(view)
        fragmentBinding = binding

    }


    override fun onResume() {
        super.onResume()
        initializeUI()
        initializeObservers()
        val code = requireArguments().getString("countryCode")
        code?.let { viewModel.onPopulationDataReady(it) }

    }

    private fun initializeObservers() {
        viewModel.loadingStateLiveData.observe(this, Observer {
            onLoadingStateChanged(it)
        })

        viewModel.countryDetailsLiveData.observe(this, Observer {
            adapter.updateCountry(it)
        })


    }

    private fun onLoadingStateChanged(state: LoadingState) {
        fragmentBinding.countryDetailsRV.visibility =
            if (state == LoadingState.LOADED) View.VISIBLE else View.GONE
        fragmentBinding.errorTV.visibility =
            if (state == LoadingState.ERROR) View.VISIBLE else View.GONE
        fragmentBinding.loadingPB.visibility =
            if (state == LoadingState.LOADING) View.VISIBLE else View.GONE
    }

    private fun initializeUI() {
        fragmentBinding.countryDetailsRV.adapter = adapter
        fragmentBinding.countryDetailsRV.layoutManager = LinearLayoutManager(view?.context)
    }

}