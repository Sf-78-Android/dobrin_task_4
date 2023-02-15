package com.training.countriesapp.api

data class CountryPopulation(
    val alpha2Code: String,
    val population: Int,
    val independent: Boolean
) : java.io.Serializable