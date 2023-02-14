package com.training.countriesapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.training.countriesapp.MainActivity
import com.training.countriesapp.R
import com.training.countriesapp.constants.ConnectionCheck
import com.training.countriesapp.constants.Constants.NO_CONNECTION
import com.training.countriesapp.constants.Constants.RETRY

class NoInternetActivity : AppCompatActivity() {
    private lateinit var mButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_no_internet)
        mButton = findViewById(R.id.refreshBtn)
        val layout: View = findViewById(R.id.no_internet_layout)
        val snackbar = Snackbar
            .make(layout, NO_CONNECTION, Snackbar.LENGTH_LONG)
            .setAction(RETRY, View.OnClickListener {
                if (ConnectionCheck(this).checkInternetConnection()) {
                    val intent = Intent(this, MainActivity::class.java)
                    this.startActivity(intent)
                }
            })
        snackbar.show()

        mButton.setOnClickListener {
            if (ConnectionCheck(this).checkInternetConnection()) {
                val intent = Intent(this, MainActivity::class.java)
                this.startActivity(intent)
            } else {
                snackbar.show()
            }
        }
    }
}
