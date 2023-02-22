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
        assertThat(data?.size).isEqualTo(3)
    }

    @Test
    fun `country details data params should be equal to 10 and the params are correct`() {
        viewModel.onPopulationDataReady("BG")
        val data = viewModel.countryDetailsLiveData.getOrAwaitValueTest()
        assertThat(data.size).isEqualTo(10)
        assertThat(data[9]).isEqualTo("9.123456")
        assertThat(data[0]).isEqualTo("0.BG")
        assertThat(data[1]).isEqualTo("1.Bulgaria")

    }

    @Test
    fun `check if countries live data information matches the inserted one`() {
        viewModel.onContinentDataReady()
        val data = viewModel.continentsLiveData.getOrAwaitValueTest()
        assertThat(data?.size).isEqualTo(1)
    }

    @Test
    fun `check if search by query works`() {
        val liveData = viewModel.fetchCountriesByQuery("rom")
        val data = liveData.getOrAwaitValueTest()
        assertThat(data?.size).isEqualTo(1)
    }

    @Test
    fun `check if search by query works for more than one country`() {
        val liveData = viewModel.fetchCountriesByQuery("gree")
        val data = liveData.getOrAwaitValueTest()
        assertThat(data?.size).isEqualTo(2)
    }

    @Test
    fun `check if search is size 0 when nothing found `() {
        val liveData = viewModel.fetchCountriesByQuery("ctatatra")
        val data = liveData.getOrAwaitValueTest()
        assertThat(data?.size).isEqualTo(0)
    }

}