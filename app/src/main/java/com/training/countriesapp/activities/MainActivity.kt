package com.training.countriesapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.training.countriesapp.R
import com.training.countriesapp.constants.ConnectionCheck
import com.training.countriesapp.constants.Constants


class MainActivity : AppCompatActivity() {
    private lateinit var btnCountryList: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val layout: View = findViewById(R.id.mainView)
        val snackBar = Snackbar
            .make(layout, Constants.NO_CONNECTION, Snackbar.LENGTH_LONG)
            .setAction(Constants.RETRY, View.OnClickListener {
                if (ConnectionCheck(this).checkInternetConnection()) {
                    val intent = Intent(this, MainActivity::class.java)
                    this.startActivity(intent)
                }
            })

        btnCountryList = findViewById(R.id.btnListCountries)

        btnCountryList.setOnClickListener {
            if (ConnectionCheck(this).checkInternetConnection()) {
                val intent = Intent(this, CountryListActivity::class.java)
                startActivity(intent)
            } else {
                snackBar.show()
            }
        }
    }

}