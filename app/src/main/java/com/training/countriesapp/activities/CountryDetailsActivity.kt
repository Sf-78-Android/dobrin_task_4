package com.training.countriesapp.activities

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.training.countriesapp.CountryDetailsQuery
import com.training.countriesapp.R
import com.training.countriesapp.apolloClient
import com.training.countriesapp.constants.Constants
import com.training.countriesapp.constants.Constants.PHONE_PREFIX
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import kotlin.coroutines.coroutineContext

class CountryDetailsActivity : AppCompatActivity()  {
   private lateinit var tvCountryName : TextView
   private lateinit var tvCountryCapital : TextView
   private lateinit var tvCountryRegion : TextView
   private lateinit var ivFlag : ImageView
   private lateinit var tvNative : TextView
   private lateinit var tvPhonePrefix: TextView
   private lateinit var tvCurrency: TextView
   private lateinit var tvLanguages: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.country_view_details)
        val display = supportActionBar
        display?.title = "Country Details"
        display?.setDisplayHomeAsUpEnabled(true)
        val intent = intent
        tvCountryName = findViewById(R.id.tvCountryName)
        tvCountryCapital= findViewById(R.id.tvCountryCapital)
        tvCountryRegion = findViewById(R.id.tvCountryRegion)
        ivFlag = findViewById(R.id.ivFlag)
        tvNative = findViewById(R.id.tvNativeName)
        tvPhonePrefix = findViewById(R.id.tvPhonePrefix)
        tvCurrency = findViewById(R.id.tvCurrency)
        tvLanguages = findViewById(R.id.tvLanguages)

        GlobalScope.launch {

            val code = intent.getStringExtra("code")
            val response = apolloClient.query(CountryDetailsQuery(code.toString())).execute()

            Log.i("CountryList", "Success ${response.data}")

            runOnUiThread {
                tvCountryName.text = response.data?.country?.name
                tvCountryCapital.text = response.data?.country?.capital
                tvCountryRegion.text = response.data?.country?.continent?.name
                tvNative.text = response.data?.country?.native
                tvPhonePrefix.text =String.format(PHONE_PREFIX, response.data?.country?.phone)
                tvCurrency.text = response.data?.country?.currency
                val languages = response.data?.country?.languages

                if (languages != null) {
                    tvLanguages.text = languages[0].name
                    for (language in 1 until languages.size){
                        tvLanguages.append(", ${languages[language].name}")
                    }


                        Glide.with(this@CountryDetailsActivity)
                            .load(String.format(Constants.FLAGS_LINK,response.data?.country?.code?.lowercase()))
                            .into(ivFlag)
                }
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