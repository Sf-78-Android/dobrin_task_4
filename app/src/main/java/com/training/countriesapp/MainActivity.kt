package com.training.countriesapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.training.countriesapp.activities.CountryListActivity


class MainActivity : AppCompatActivity() {
    private lateinit var btnCountryList : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnCountryList = findViewById(R.id.btnListCountries)

        btnCountryList.setOnClickListener {
            val intent = Intent(this, CountryListActivity::class.java)
            startActivity(intent)
        }
    }
}