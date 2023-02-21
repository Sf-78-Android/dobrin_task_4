package com.training.countriesapp.repo

import com.training.countriesapp.ContinentsListQuery
import com.training.countriesapp.CountryDetailsQuery
import com.training.countriesapp.CountryListQuery

interface RepositoryInterface {

    suspend fun getContinents(): List<ContinentsListQuery.Continent>?

    suspend fun getCountryData(code: String): CountryDetailsQuery.Country?

    fun getPopulationData(code: String): Int?

    fun getArea(code: String): Double?

    suspend fun getAllCountries(): List<CountryListQuery.Country>?

    suspend fun searchCountries(query: String): List<CountryListQuery.Country>?
}