package com.training.countriesapp.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.training.countriesapp.R
import com.training.countriesapp.adapter.ContinentAdapter
import com.training.countriesapp.databinding.FragmentContinentListBinding
import com.training.countriesapp.model.LoadingState
import com.training.countriesapp.viewmodel.CountryViewModel

class ContinentListFragment : Fragment(R.layout.fragment_continent_list) {
    private val viewModel: CountryViewModel by viewModels()
    private lateinit var fragmentBinding: FragmentContinentListBinding
    private var adapter = ContinentAdapter(mutableListOf())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentContinentListBinding.bind(view)
        fragmentBinding = binding

    }


    override fun onResume() {
        super.onResume()
        initializeUI()
        initializeObservers()

        viewModel.onContinentDataReady()

    }

    private fun initializeObservers() {
        viewModel.loadingStateLiveData.observe(this, Observer {
            onLoadingStateChanged(it)
        })

        viewModel.continentsLiveData.observe(this, Observer {
            adapter.updateContinents(it)
        })
    }

    private fun onLoadingStateChanged(state: LoadingState) {
        fragmentBinding.continentRV.visibility =
            if (state == LoadingState.LOADED) View.VISIBLE else View.GONE
        fragmentBinding.errorTV.visibility =
            if (state == LoadingState.ERROR) View.VISIBLE else View.GONE
        fragmentBinding.loadingPB.visibility =
            if (state == LoadingState.LOADING) View.VISIBLE else View.GONE
    }

    private fun initializeUI() {
        fragmentBinding.continentRV.adapter = adapter
        fragmentBinding.continentRV.layoutManager = LinearLayoutManager(view?.context)
    }


}