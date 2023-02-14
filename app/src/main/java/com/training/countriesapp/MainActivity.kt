package com.training.countriesapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.training.countriesapp.activities.CountryListActivity
import com.training.countriesapp.activities.NoInternetActivity
import com.training.countriesapp.constants.ConnectionCheck


class MainActivity : AppCompatActivity() {
    private lateinit var btnCountryList: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCountryList = findViewById(R.id.btnListCountries)

        btnCountryList.setOnClickListener {
            if (ConnectionCheck(this).checkInternetConnection()) {
                val intent = Intent(this, CountryListActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, NoInternetActivity::class.java)
                this.startActivity(intent)
            }
        }
    }


}