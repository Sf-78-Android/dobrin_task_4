package com.training.countriesapp.model


import android.util.Log
import com.training.countriesapp.CountryListQuery
import com.training.countriesapp.api.Apollo

object CountriesMainDataProvider {
    private var countries : List<CountryListQuery.Country>? = mutableListOf()

    suspend fun getAllCountries(): List<CountryListQuery.Country>? {
       val response = Apollo.apolloClient.query(CountryListQuery()).execute()
        Log.i("Response", "${response.data}")
        countries = response.data?.countries
        return countries
    }


    suspend fun searchCountries(query:String):List<CountryListQuery.Country>?{
      return  getAllCountries()?.filter { country ->
            country.name.lowercase().contains(query.lowercase())
        }
    }

}