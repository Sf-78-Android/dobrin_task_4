package com.training.countriesapp.model

import android.util.Log
import com.training.countriesapp.CountryDetailsQuery
import com.training.countriesapp.api.Apollo
import com.training.countriesapp.api.Retrofit
import kotlinx.coroutines.delay

object CountriesPopulationDataProvider {
    private var country : CountryDetailsQuery.Country? = null

    suspend fun getCountryData(code:String): CountryDetailsQuery.Country? {
        val response = Apollo.apolloClient.query(CountryDetailsQuery(code)).execute()
        Log.i("Response", "${response.data}")
        country = response.data?.country
        return country
    }

    suspend fun getPopulationData(code: String): Int? {
        delay(2000L)
        return Retrofit.getPopulation(code)
    }

}