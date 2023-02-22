package com.training.countriesapp.viewmodel

import androidx.lifecycle.*
import com.training.countriesapp.ContinentsListQuery
import com.training.countriesapp.CountryListQuery
import com.training.countriesapp.constants.Constants.DEBOUNCE_PERIOD
import com.training.countriesapp.repo.LoadingState
import com.training.countriesapp.repo.LoadingState.*
import com.training.countriesapp.repo.RepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CountryViewModel @Inject constructor(
    private var repository: RepositoryInterface
) : ViewModel() {
    val countriesLiveData = MediatorLiveData<List<CountryListQuery.Country>?>()
    val continentsLiveData = MediatorLiveData<List<ContinentsListQuery.Continent>?>()
    val countryDetailsLiveData = MutableLiveData<List<String>>()
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
        loadingStateLiveData.value = LOADING
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val countries = repository.getAllCountries()
                _allCountriesLiveData.postValue(countries)
                loadingStateLiveData.postValue(LOADED)

            } catch (e: java.lang.Exception) {
                loadingStateLiveData.postValue(ERROR)
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

    fun fetchCountriesByQuery(query: String): LiveData<List<CountryListQuery.Country>?> {
        val liveData = MutableLiveData<List<CountryListQuery.Country>?>()
        loadingStateLiveData.value = LOADING
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val countries = repository.searchCountries(query)
                liveData.postValue(countries)
                loadingStateLiveData.postValue(LOADED)
            } catch (e: java.lang.Exception) {
                loadingStateLiveData.postValue(ERROR)
            }
        }
        return liveData
    }

    fun onPopulationDataReady(code: String) {
        val list: MutableList<String> = mutableListOf()

        loadingStateLiveData.value = LOADING
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val country = repository.getCountryData(code)
                val population = repository.getPopulationData(code)
                val area = repository.getArea(code)
                country?.let {
                    list.add("0.${country.code}")
                    list.add("1.${country.name}")
                    list.add("2.${country.capital ?: "Not available"}")
                    list.add("3.${country.continent.name}")
                    list.add("4.${country.native}")
                    list.add("5.${country.phone}")
                    var languagesList = ""
                    if (!country.languages.isNullOrEmpty()) {
                        for (language in country.languages) {
                            languagesList += if (country.languages.indexOf(language) < country.languages.size - 1) {
                                "${language.name ?: "Not available"} - ${language.native ?: "Not available"}\n"
                            } else {
                                "${language.name ?: "Not available"} - ${language.native ?: "Not available"}"
                            }
                        }
                    } else {
                        languagesList = "Not available"
                    }

                    list.add("6.${languagesList}")
                    list.add("7.${country.currency ?: "Not available"}")
                    list.add("8.${area ?: "Not available"}")
                    list.add("9.${population ?: "Not available"}")
                }
                countryDetailsLiveData.postValue(list)
                loadingStateLiveData.postValue(LOADED)
            } catch (e: java.lang.Exception) {
                loadingStateLiveData.postValue(ERROR)
            }
        }
    }

    fun onContinentDataReady() {
        loadingStateLiveData.value = LOADING
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val continents = repository.getContinents()
                continentsLiveData.postValue(continents)
                loadingStateLiveData.postValue(LOADED)
            } catch (e: java.lang.Exception) {
                loadingStateLiveData.postValue(ERROR)
            }
        }
    }

}