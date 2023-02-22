package com.training.countriesapp.repo

import com.training.countriesapp.ContinentsListQuery
import com.training.countriesapp.CountryDetailsQuery
import com.training.countriesapp.CountryListQuery
import com.training.countriesapp.api.CountryAdditionalData

class FakeRepository : RepositoryInterface {
    private var country: CountryDetailsQuery.Country
    private var additionalData: CountryAdditionalData
    private var countries = mutableListOf<CountryListQuery.Country>()
    private var continents = mutableListOf<ContinentsListQuery.Continent>()


    init {
        val language = CountryDetailsQuery.Language(name = "Bulgarian", native = "Български")
        country = CountryDetailsQuery.Country(
            code = "BG",
            name = "Bulgaria",
            native = "България",
            phone = "359",
            continent = CountryDetailsQuery.Continent(name = "Europe"),
            capital = "Sofia",
            currency = "BGN",
            languages = mutableListOf(language)
        )
        additionalData =
            CountryAdditionalData(alpha2Code = "BG", population = 123456, 100000.00, true)

        val continent1 = ContinentsListQuery.Continent(
            name = "Europe",
            countries = listOf(
                ContinentsListQuery.Country(name = "Bulgaria"),
                ContinentsListQuery.Country(name = "Romania"),
                ContinentsListQuery.Country(name = "Greece"),
                ContinentsListQuery.Country(name = "Macedonia"),
                ContinentsListQuery.Country(name = "Serbia")
            )
        )
        continents.add(continent1)

        val country2 = CountryListQuery.Country(
            name = "Greece",
            code = "GR",
            continent = CountryListQuery.Continent(name = "Europe"),
            capital = "Athens"

        )

        val country3 = CountryListQuery.Country(
            name = "Romania",
            code = "RO",
            continent = CountryListQuery.Continent(name = "Europe"),
            capital = "Bucharest"

        )
        val country4 = CountryListQuery.Country(
            name = "Greenland",
            code = "GL",
            continent = CountryListQuery.Continent(name = "North America"),
            capital = "Nuuk"

        )

        countries.add(country2)
        countries.add(country3)
        countries.add(country4)

    }


    override suspend fun getContinents(): List<ContinentsListQuery.Continent> {
        return continents
    }

    override suspend fun getCountryData(code: String): CountryDetailsQuery.Country {
        return country
    }

    override fun getPopulationData(code: String): Int {
        if (code.lowercase() == additionalData.alpha2Code.lowercase()) {
            return additionalData.population
        }
        return 0
    }

    override fun getArea(code: String): Double {
        return additionalData.area
    }

    override suspend fun getAllCountries(): List<CountryListQuery.Country> {
        return countries
    }

    override suspend fun searchCountries(query: String): List<CountryListQuery.Country> {
        return getAllCountries().filter { country ->
            country.name.lowercase().contains(query.lowercase())
        }
    }
}