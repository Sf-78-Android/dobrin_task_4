package com.training.countriesapp.viewmodel

import android.view.animation.Transformation
import androidx.lifecycle.*
import com.training.countriesapp.CountryListQuery
import com.training.countriesapp.model.CountriesDataProvider
import com.training.countriesapp.model.LoadingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CountryViewModel : ViewModel() {
    val countriesLiveData = MediatorLiveData<List<CountryListQuery.Country>?>()
    private val _queryLiveData = MutableLiveData<String>()
    private val _allCountriesLiveData = MutableLiveData<List<CountryListQuery.Country>?>()
    private var _searchCountriesLiveData: LiveData<List<CountryListQuery.Country>?>
    val loadingStateLiveData = MutableLiveData<LoadingState>()
    private var searchJob: Job? = null
    private val debouncePeriod = 500L

    init {
        _searchCountriesLiveData = Transformations.switchMap(_queryLiveData) {
            fetchCountriesByQuery(it)
        }
        countriesLiveData.addSource(_allCountriesLiveData) {
            countriesLiveData.value = it
        }
        countriesLiveData.addSource(_searchCountriesLiveData) {
            countriesLiveData.value = it
        }

    }

    fun onViewReady() {
        if (_allCountriesLiveData.value.isNullOrEmpty()) {
            fetchAllCountries()
        }
    }

    private fun fetchAllCountries() {
        loadingStateLiveData.value = LoadingState.LOADING
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val countries = CountriesDataProvider.getAllCountries()
                _allCountriesLiveData.postValue(countries)
                loadingStateLiveData.postValue(LoadingState.LOADED)
            } catch (e: java.lang.Exception) {
                loadingStateLiveData.postValue(LoadingState.ERROR)
            }
        }
    }


    fun onSearchQuery(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(debouncePeriod)
            if (query.isEmpty()) {
                fetchAllCountries()
            } else {
                _queryLiveData.postValue(query)
            }
        }
    }

    private fun fetchCountriesByQuery(query: String): LiveData<List<CountryListQuery.Country>?> {
        val liveData = MutableLiveData<List<CountryListQuery.Country>?>()
        loadingStateLiveData.value = LoadingState.LOADING
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val countries = CountriesDataProvider.searchCountries(query)
                liveData.postValue(countries)
                loadingStateLiveData.postValue(LoadingState.LOADED)
            } catch (e: java.lang.Exception) {
                loadingStateLiveData.postValue(LoadingState.ERROR)
            }
        }
        return liveData
    }

}