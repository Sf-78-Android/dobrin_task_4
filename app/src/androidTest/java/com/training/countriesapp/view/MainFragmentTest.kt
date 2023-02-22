package com.training.countriesapp.view

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.training.countriesapp.R
import com.training.countriesapp.launchFragmentInHiltContainer
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
class MainFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: CountryFragmentFactory

    @Before
    fun setup() {
        hiltRule.inject()

    }

    @Test
    fun testNavigationFromMainToCountryList() {
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<MainFragment>(
            factory = fragmentFactory
        ) {
            Navigation.setViewNavController(requireView(), navController)
        }

        Espresso
            .onView(ViewMatchers.withId(R.id.btnListCountries))
            .perform(ViewActions.click())

        Mockito.verify(navController).navigate(
            MainFragmentDirections.actionMainFragmentToCountryListFragment()
        )
    }

    @Test
    fun testNavigationFromMainToContinentList() {
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<MainFragment>(
            factory = fragmentFactory
        ) {
            Navigation.setViewNavController(requireView(), navController)
        }

        Espresso
            .onView(ViewMatchers.withId(R.id.btnListContinents))
            .perform(ViewActions.click())

        Mockito.verify(navController).navigate(
            MainFragmentDirections.actionMainFragmentToContinentListFragment()
        )
    }

}