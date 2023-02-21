package com.training.countriesapp.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.training.countriesapp.adapter.ContinentAdapter
import com.training.countriesapp.adapter.CountryAdapter
import com.training.countriesapp.adapter.DetailsAdapter
import javax.inject.Inject

class CountryFragmentFactory @Inject constructor(
    private val countryAdapter: CountryAdapter,
    private val continentAdapter: ContinentAdapter,
    private val detailsAdapter: DetailsAdapter

) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            CountryListFragment::class.java.name -> CountryListFragment(countryAdapter)
            ContinentListFragment::class.java.name -> ContinentListFragment(continentAdapter)
            CountryDetailsFragment::class.java.name -> CountryDetailsFragment(detailsAdapter)
            else -> super.instantiate(classLoader, className)
        }
    }
}