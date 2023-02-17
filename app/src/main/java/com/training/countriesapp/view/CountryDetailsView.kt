package com.training.countriesapp.view

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.training.countriesapp.R
import com.training.countriesapp.adapter.DetailsAdapter
import com.training.countriesapp.databinding.ActivityCountryDetailsBinding
import com.training.countriesapp.model.LoadingState
import com.training.countriesapp.viewmodel.CountryViewModel

class CountryDetailsView : AppCompatActivity() {
    private val viewModel: CountryViewModel by viewModels()
    private lateinit var binding: ActivityCountryDetailsBinding
    private var adapter = DetailsAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountryDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val display = supportActionBar
        display?.title = getString(R.string.country_details)
        display?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onResume() {
        super.onResume()
        initializeUI()
        initializeObservers()

        intent.getStringExtra("code")?.let { viewModel.onPopulationDataReady(it) }

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
        binding.countryDetailsRV.visibility =
            if (state == LoadingState.LOADED) View.VISIBLE else View.GONE
        binding.errorTV.visibility = if (state == LoadingState.ERROR) View.VISIBLE else View.GONE
        binding.loadingPB.visibility =
            if (state == LoadingState.LOADING) View.VISIBLE else View.GONE
    }

    private fun initializeUI() {
        binding.countryDetailsRV.adapter = adapter
        binding.countryDetailsRV.layoutManager = LinearLayoutManager(this)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}