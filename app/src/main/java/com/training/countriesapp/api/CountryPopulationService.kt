package com.training.countriesapp.api

import retrofit2.Call
import retrofit2.http.GET

interface CountryPopulationService {

    @GET("all?fields=alpha2Code,population,area")
    fun getAdditionalData(
    ): Call<List<CountryAdditionalData>>
}