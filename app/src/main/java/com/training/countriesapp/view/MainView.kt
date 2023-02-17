package com.training.countriesapp.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.training.countriesapp.constants.ConnectionCheck
import com.training.countriesapp.constants.Constants
import com.training.countriesapp.databinding.ActivityMainBinding


class MainView : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val snackBar = Snackbar
            .make(binding.mainView, Constants.NO_CONNECTION, Snackbar.LENGTH_LONG)
            .setAction(Constants.RETRY, View.OnClickListener {
                if (ConnectionCheck(this).checkInternetConnection()) {
                    val intent = Intent(this, MainView::class.java)
                    this.startActivity(intent)
                }
            })


        binding.btnListCountries.setOnClickListener {
            if (ConnectionCheck(this).checkInternetConnection()) {
                val intent = Intent(this, CountryListView::class.java)
                startActivity(intent)
            } else {
                snackBar.show()
            }
        }

        binding.btnListContinents.setOnClickListener {
            if (ConnectionCheck(this).checkInternetConnection()) {
                val intent = Intent(this, ContinentView::class.java)
                startActivity(intent)
            } else {
                snackBar.show()
            }
        }
    }

}