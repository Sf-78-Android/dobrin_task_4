package com.training.countriesapp.api

data class CountryAdditionalData(
    val alpha2Code: String,
    val population: Int,
    val area: Double,
    val independent: Boolean
) : java.io.Serializable