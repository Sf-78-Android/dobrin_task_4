package com.training.countriesapp.repo

import android.util.Log
import com.bumptech.glide.Glide.init
import com.training.countriesapp.ContinentsListQuery
import com.training.countriesapp.CountryDetailsQuery
import com.training.countriesapp.CountryListQuery
import com.training.countriesapp.api.Apollo
import com.training.countriesapp.api.Retrofit
import javax.inject.Inject


class Repository @Inject constructor() : RepositoryInterface {
    private var country: CountryDetailsQuery.Country? = null
    private var countries: List<CountryListQuery.Country>? = mutableListOf()

    init {
        Retrofit.getAdditionalData()
    }

    override suspend fun getContinents(): List<ContinentsListQuery.Continent>? {
        val response = Apollo.apolloClient.query(ContinentsListQuery()).execute()
        Log.i("Response", "${response.data}")
        return response.data?.continents
    }

    override suspend fun getCountryData(code: String): CountryDetailsQuery.Country? {
        val response = Apollo.apolloClient.query(CountryDetailsQuery(code)).execute()
        Log.i("Response", "${response.data}")
        country = response.data?.country
        return country
    }

    override fun getPopulationData(code: String): Int? {
        return Retrofit.getPopulation(code)
    }

    override fun getArea(code: String): Double? {
        return Retrofit.getArea(code)
    }

    override suspend fun getAllCountries(): List<CountryListQuery.Country>? {
        val response = Apollo.apolloClient.query(CountryListQuery()).execute()
        Log.i("Response", "${response.data}")
        countries = response.data?.countries
        return countries
    }

    override suspend fun searchCountries(query: String): List<CountryListQuery.Country>? {
        return getAllCountries()?.filter { country ->
            country.name.lowercase().contains(query.lowercase())
        }
    }
}