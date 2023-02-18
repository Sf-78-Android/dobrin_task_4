package com.training.countriesapp.view

import android.app.ActionBar
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import com.training.countriesapp.R
import com.training.countriesapp.adapter.CountryAdapter
import com.training.countriesapp.databinding.FragmentCountryListBinding
import com.training.countriesapp.model.LoadingState
import com.training.countriesapp.viewmodel.CountryViewModel

class CountryListFragment : Fragment(R.layout.fragment_country_list) {
    private val viewModel: CountryViewModel by viewModels()
    private lateinit var fragmentBinding: FragmentCountryListBinding
    private var adapter = CountryAdapter(mutableListOf())


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentCountryListBinding.bind(view)
        fragmentBinding = binding
        setHasOptionsMenu(true)
    }

    override fun onResume() {
        super.onResume()
        initializeUI()
        initializeObservers()

        viewModel.onViewReady()

    }

    private fun initializeObservers() {
        viewModel.loadingStateLiveData.observe(this, Observer {
            onLoadingStateChanged(it)
        })

        viewModel.countriesLiveData.observe(this, Observer {
            adapter.updateCountries(it)
        })
    }

    private fun onLoadingStateChanged(state: LoadingState) {
        fragmentBinding.countriesRV.visibility =
            if (state == LoadingState.LOADED) View.VISIBLE else View.GONE
        fragmentBinding.errorTV.visibility =
            if (state == LoadingState.ERROR) View.VISIBLE else View.GONE
        fragmentBinding.loadingPB.visibility =
            if (state == LoadingState.LOADING) View.VISIBLE else View.GONE
    }

    private fun initializeUI() {
        fragmentBinding.countriesRV.adapter = adapter
        fragmentBinding.countriesRV.layoutManager = LinearLayoutManager(view?.context)

    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_country_list, menu)
        val searchItem = menu.findItem(R.id.menu_search)
        if (searchItem != null) {
            val searchView = searchItem.actionView as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let { viewModel.onSearchQuery(it) }
                    return true
                }

            })
        }
    }

}

