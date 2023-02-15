package com.training.countriesapp.api

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {
    private var result: List<CountryPopulation>? = null
    var url = "https://restcountries.com/v2/"



    fun getPopulation(code: String?): Int? {
       return result?.find { countryPopulation -> countryPopulation.alpha2Code == code }?.population

    }

    fun getPopulationData() {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(String.format(url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service: CountryPopulationService = retrofit
            .create(CountryPopulationService::class.java)

        val listCall: Call<List<CountryPopulation>> = service
            .getPopulation()


        listCall.enqueue(object : Callback<List<CountryPopulation>> {

            override fun onResponse(
                call: Call<List<CountryPopulation>>,
                response: Response<List<CountryPopulation>>
            ) {
                if (response.isSuccessful) {
                    result = response.body()

                    Log.i("RESULT_RESPONSE", "$result")

                } else {
                    when (response.code()) {
                        400 -> {
                            Log.e("ERROR_400", "BAD_CONNECTION")
                        }
                        404 -> {
                            Log.e("ERROR_404", "RESOURCE_NOT_FOUND")
                        }
                        else -> {
                            Log.e("ERROR", "GENERIC_ERROR")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<CountryPopulation>>, t: Throwable) {
                Log.e("ERROR", t.message.toString())
            }
        })
    }
}