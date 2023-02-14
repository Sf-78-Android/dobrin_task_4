package com.training.countriesapp.activities

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.training.countriesapp.CountryListQuery
import com.training.countriesapp.R
import com.training.countriesapp.adapter.ItemAdapter
import com.training.countriesapp.apolloClient
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CountryListActivity : AppCompatActivity() {
   private var displayList : MutableList<CountryListQuery.Country> = mutableListOf()
   private var countries : MutableList<CountryListQuery.Country>? = null
   private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_list)
        val display = supportActionBar
        display?.title = "Country List"
        display?.setDisplayHomeAsUpEnabled(true)
         recyclerView = findViewById(R.id.recycler_view)
        // Initialize data.
        GlobalScope.launch {
            val response = apolloClient.query(CountryListQuery()).execute()

            Log.i("CountryList", "Success ${response.data}")


            countries = response.data?.countries as MutableList<CountryListQuery.Country>

            Log.i("CountryList", "Success $countries")
            runOnUiThread {
                recyclerView.adapter = ItemAdapter(this@CountryListActivity, countries)
                recyclerView.setHasFixedSize(true)
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.country_list,menu)
        val searchItem = menu?.findItem(R.id.menu_search)
        if (searchItem != null) {
            val searchView = searchItem.actionView as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (!newText.isNullOrEmpty()){
                        displayList.clear()
                        val search = newText.lowercase()
                        countries?.forEach {
                            if (it.name.lowercase().contains(search)){
                                displayList.add(it)
                            }
                        }
                        recyclerView.adapter = ItemAdapter(this@CountryListActivity, displayList)

                    }else {
                        displayList.clear()
                        displayList.addAll(countries as MutableList)
                        recyclerView.adapter = ItemAdapter(this@CountryListActivity, countries)
                    }


                   return true
                }

            })
        }
        return true
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