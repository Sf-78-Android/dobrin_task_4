package com.training.countriesapp.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.training.countriesapp.R
import com.training.countriesapp.adapter.CountryAdapter
import com.training.countriesapp.getOrAwaitValueTest
import com.training.countriesapp.launchFragmentInHiltContainer
import com.training.countriesapp.repo.FakeRepository
import com.training.countriesapp.viewmodel.CountryViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class CountryListFragmentTest {
    private var countryViewModel = CountryViewModel(FakeRepository())

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @Inject
    lateinit var fragmentFactory: CountryFragmentFactory

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testNavigationFromCountryListToCountryDetailsFirstElement() {
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<CountryListFragment>(
            factory = fragmentFactory
        ) {
            Navigation.setViewNavController(requireView(), navController)
            adapter.updateCountries(viewModel.countriesLiveData.getOrAwaitValueTest())
        }

        Espresso.onView(ViewMatchers.withId(R.id.countriesRV)).perform(
            RecyclerViewActions.scrollToPosition<CountryAdapter.CountryViewHolder>(1),
            RecyclerViewActions.actionOnItemAtPosition<CountryAdapter.CountryViewHolder>(
                0, click()
            )
        )


        Mockito.verify(navController).navigate(
            CountryListFragmentDirections.actionCountryListFragmentToCountryDetailsFragment("AD")
        )

    }
}