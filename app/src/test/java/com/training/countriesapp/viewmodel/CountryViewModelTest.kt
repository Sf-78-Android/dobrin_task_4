package com.training.countriesapp.viewmodel


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.training.countriesapp.MainCoroutineRule
import com.training.countriesapp.getOrAwaitValueTest
import com.training.countriesapp.repo.FakeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

const val EXPECTED_COUNTRY_COUNT = 3
const val EXPECTED_COUNTRY_PARAMS = 10
const val EXPECTED_POPULATION_PARAM = "9.123456"
const val EXPECTED_CODE_PARAM = "0.BG"
const val EXPECTED_NAME_PARAM = "1.Bulgaria"
const val COUNTRY_CODE = "BG"
const val ONE = 1
const val TWO = 2
const val ZERO = 0
const val CORRECT_SEARCH_QUERY = "gree"
const val INCORRECT_SEARCH_QUERY = "ctatatra"

@ExperimentalCoroutinesApi
class CountryViewModelTest {
    private lateinit var viewModel: CountryViewModel


    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        //Test Doubles
        viewModel = CountryViewModel(FakeRepository())
        viewModel.onViewReady()
    }

    @Test
    fun `test if countriesLiveData fetches all the countries`() {
        val data = viewModel.countriesLiveData.getOrAwaitValueTest()
        assertThat(data?.size).isEqualTo(EXPECTED_COUNTRY_COUNT)
    }

    @Test
    fun `country details data params should be equal to 10 and the params are correct`() {
        viewModel.onPopulationDataReady(COUNTRY_CODE)
        val data = viewModel.countryDetailsLiveData.getOrAwaitValueTest()
        assertThat(data.size).isEqualTo(EXPECTED_COUNTRY_PARAMS)
        assertThat(data[9]).isEqualTo(EXPECTED_POPULATION_PARAM)
        assertThat(data[0]).isEqualTo(EXPECTED_CODE_PARAM)
        assertThat(data[1]).isEqualTo(EXPECTED_NAME_PARAM)

    }

    @Test
    fun `check if countries live data information matches the inserted one`() {
        viewModel.onContinentDataReady()
        val data = viewModel.continentsLiveData.getOrAwaitValueTest()
        assertThat(data?.size).isEqualTo(ONE)
    }

    @Test
    fun `check if search by query works`() {
        val liveData = viewModel.fetchCountriesByQuery("rom")
        val data = liveData.getOrAwaitValueTest()
        assertThat(data?.size).isEqualTo(ONE)
    }

    @Test
    fun `check if search by query works for more than one country`() {
        val liveData = viewModel.fetchCountriesByQuery(CORRECT_SEARCH_QUERY)
        val data = liveData.getOrAwaitValueTest()
        assertThat(data?.size).isEqualTo(TWO)
    }

    @Test
    fun `check if search is size 0 when nothing found `() {
        val liveData = viewModel.fetchCountriesByQuery(INCORRECT_SEARCH_QUERY)
        val data = liveData.getOrAwaitValueTest()
        assertThat(data?.size).isEqualTo(ZERO)
    }

}