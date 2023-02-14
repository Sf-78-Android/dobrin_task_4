package com.training.countriesapp.activities

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.training.countriesapp.CountryListQuery
import com.training.countriesapp.R
import com.training.countriesapp.adapter.ItemAdapter
import com.training.countriesapp.apolloClient
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CountryListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_list)
        val display = supportActionBar
        display?.title = "Country List"
        display?.setDisplayHomeAsUpEnabled(true)
        // Initialize data.
        GlobalScope.launch {
            val response = apolloClient.query(CountryListQuery()).execute()

            Log.i("CountryList", "Success ${response.data}")

            runOnUiThread {

                val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
                recyclerView.adapter =
                    ItemAdapter(this@CountryListActivity, response.data?.countries)
                recyclerView.setHasFixedSize(true)
            }

        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}