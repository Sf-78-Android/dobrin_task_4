package com.training.countriesapp.activities

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.training.countriesapp.CountryDetailsQuery
import com.training.countriesapp.R
import com.training.countriesapp.api.Retrofit
import com.training.countriesapp.api.apolloClient
import com.training.countriesapp.constants.Constants
import com.training.countriesapp.constants.Constants.PHONE_PREFIX
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CountryDetailsActivity : AppCompatActivity() {
    private lateinit var tvCountryName: TextView
    private lateinit var tvCountryCapital: TextView
    private lateinit var tvCountryRegion: TextView
    private lateinit var ivFlag: ImageView
    private lateinit var tvNative: TextView
    private lateinit var tvPhonePrefix: TextView
    private lateinit var tvCurrency: TextView
    private lateinit var tvLanguages: TextView
    private lateinit var tvPopulation: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_view_details)
        val display = supportActionBar
        display?.title = getString(R.string.country_details)
        display?.setDisplayHomeAsUpEnabled(true)
        val intent = intent
        tvCountryName = findViewById(R.id.tvCountryName)
        tvCountryCapital = findViewById(R.id.tvCountryCapital)
        tvCountryRegion = findViewById(R.id.tvCountryRegion)
        ivFlag = findViewById(R.id.ivFlag)
        tvNative = findViewById(R.id.tvNativeName)
        tvPhonePrefix = findViewById(R.id.tvPhonePrefix)
        tvCurrency = findViewById(R.id.tvCurrency)
        tvLanguages = findViewById(R.id.tvLanguages)
        tvPopulation = findViewById(R.id.tvPopulation)
        var population: Int? = null

        GlobalScope.launch {

            val code = intent.getStringExtra(getString(R.string.code))

            val response = apolloClient.query(CountryDetailsQuery(code.toString())).execute()

            Log.i(getString(R.string.country_list_tag), "${response.data}")


            launch {
                runOnUiThread {
                    tvCountryName.text = response.data?.country?.name
                    tvCountryCapital.text = response.data?.country?.capital
                    tvCountryRegion.text = response.data?.country?.continent?.name
                    tvNative.text = response.data?.country?.native
                    tvPhonePrefix.text =
                        String.format(PHONE_PREFIX, response.data?.country?.phone)
                    tvCurrency.text = response.data?.country?.currency
                    try {
                        tvPopulation.text =
                            Retrofit.getPopulation(response.data?.country?.code).toString()
                    } catch (e: java.lang.Exception) {
                        tvPopulation.text = getString(R.string.no_data)
                    }
                    val languages = response.data?.country?.languages

                    if (languages != null && languages.isNotEmpty()) {
                        tvLanguages.text = languages[0].name
                        for (language in 1 until languages.size) {
                            tvLanguages.append(", ${languages[language].name}")
                        }

                    } else {
                        tvLanguages.text = getString(R.string.none)
                    }
                    Glide.with(this@CountryDetailsActivity)
                        .load(
                            String.format(
                                Constants.FLAGS_LINK,
                                response.data?.country?.code?.lowercase()
                            )
                        )
                        .into(ivFlag)
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}