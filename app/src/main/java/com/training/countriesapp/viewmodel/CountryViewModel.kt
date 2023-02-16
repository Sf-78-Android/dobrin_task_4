package com.training.countriesapp.viewmodel

import androidx.lifecycle.*
import com.training.countriesapp.CountryDetailsQuery
import com.training.countriesapp.CountryListQuery
import com.training.countriesapp.constants.Constants.DEBOUNCE_PERIOD
import com.training.countriesapp.model.CountriesMainDataProvider
import com.training.countriesapp.model.CountriesPopulationDataProvider
import com.training.countriesapp.model.LoadingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CountryViewModel : ViewModel() {
    val countriesLiveData = MediatorLiveData<List<CountryListQuery.Country>?>()
    val countryDetailsLiveData = MutableLiveData<List<CountryDetailsQuery.Country>?>()
    private val _queryLiveData = MutableLiveData<String>()
    private val _allCountriesLiveData = MutableLiveData<List<CountryListQuery.Country>?>()
    private var _searchCountriesLiveData: LiveData<List<CountryListQuery.Country>?>
    val loadingStateLiveData = MutableLiveData<LoadingState>()
    private var searchJob: Job? = null
    private val debouncePeriod = DEBOUNCE_PERIOD

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
                val countries = CountriesMainDataProvider.getAllCountries()
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
                val countries = CountriesMainDataProvider.searchCountries(query)
                liveData.postValue(countries)
                loadingStateLiveData.postValue(LoadingState.LOADED)
            } catch (e: java.lang.Exception) {
                loadingStateLiveData.postValue(LoadingState.ERROR)
            }
        }
        return liveData
    }

    fun onPopulationDataReady(code: String) {
        val list:MutableList<CountryDetailsQuery.Country>? = mutableListOf()

        loadingStateLiveData.value = LoadingState.LOADING
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val countries = CountriesPopulationDataProvider.getCountryData(code)
                //val population = CountriesPopulationDataProvider.getPopulationData(code)
                countries?.let {
                        list?.add(it)
                     }
                countryDetailsLiveData.postValue(list)
                loadingStateLiveData.postValue(LoadingState.LOADED)
            } catch (e: java.lang.Exception) {
                loadingStateLiveData.postValue(LoadingState.ERROR)
            }
        }
    }


}