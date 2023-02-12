package com.training.countriesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch {
            val response = apolloClient.query(CountryListQuery()).execute()

            Log.i("CountryList", "Success ${response.data}")

            val country = apolloClient.query(CountryDetailsQuery(code = "BG")).execute()

            Log.i("CountryDetails", "Success ${country.data}")

            val continents = apolloClient.query(ContinentsListQuery()).execute()
            Log.i("ContinentList","Success ${continents.data}")
        }

    }
}