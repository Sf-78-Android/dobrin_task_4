package com.training.countriesapp.model

import android.util.Log
import com.training.countriesapp.CountryDetailsQuery
import com.training.countriesapp.api.Apollo
import com.training.countriesapp.api.Retrofit

object CountriesAdditionalDataProvider {
    private var country: CountryDetailsQuery.Country? = null


    suspend fun getCountryData(code: String): CountryDetailsQuery.Country? {
        val response = Apollo.apolloClient.query(CountryDetailsQuery(code)).execute()
        Log.i("Response", "${response.data}")
        country = response.data?.country
        return country
    }

    fun getPopulationData(code: String): Int? {
        return Retrofit.getPopulation(code)
    }

    fun getArea(code: String): Double? {
        return Retrofit.getArea(code)
    }

}