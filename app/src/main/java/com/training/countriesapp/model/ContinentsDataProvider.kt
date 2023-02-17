package com.training.countriesapp.model

import android.util.Log
import com.training.countriesapp.ContinentsListQuery
import com.training.countriesapp.api.Apollo

object ContinentsDataProvider {

    suspend fun getContinents(): List<ContinentsListQuery.Continent>? {
        val response = Apollo.apolloClient.query(ContinentsListQuery()).execute()
        Log.i("Response", "${response.data}")
        return response.data?.continents
    }
}