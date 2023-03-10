package com.training.countriesapp.api

import android.util.Log
import com.training.countriesapp.constants.Constants.BAD_CONNECTION
import com.training.countriesapp.constants.Constants.ERROR
import com.training.countriesapp.constants.Constants.ERROR_400
import com.training.countriesapp.constants.Constants.ERROR_404
import com.training.countriesapp.constants.Constants.FOUR_HUNDRED
import com.training.countriesapp.constants.Constants.FOUR_HUNDRED_AND_FOUR
import com.training.countriesapp.constants.Constants.GENERIC_ERROR
import com.training.countriesapp.constants.Constants.RESOURCE_NOT_FOUND
import com.training.countriesapp.constants.Constants.RESULT_RESPONSE
import com.training.countriesapp.constants.Constants.RETROFIT_API_LINK
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {
    private var result: List<CountryAdditionalData>? = null

     fun getAdditionalData() : List<CountryAdditionalData>?  {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(String.format(RETROFIT_API_LINK))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service: CountryPopulationService = retrofit
            .create(CountryPopulationService::class.java)

        val listCall: Call<List<CountryAdditionalData>> = service
            .getAdditionalData()


        listCall.enqueue(object : Callback<List<CountryAdditionalData>> {

            override fun onResponse(
                call: Call<List<CountryAdditionalData>>,
                response: Response<List<CountryAdditionalData>>
            ) {
                if (response.isSuccessful) {
                    result = response.body()

                    Log.i(RESULT_RESPONSE, "$result")

                } else {
                    when (response.code()) {
                        FOUR_HUNDRED -> {
                            Log.e(ERROR_400, BAD_CONNECTION)
                        }
                        FOUR_HUNDRED_AND_FOUR -> {
                            Log.e(ERROR_404, RESOURCE_NOT_FOUND)
                        }
                        else -> {
                            Log.e(ERROR, GENERIC_ERROR)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<CountryAdditionalData>>, t: Throwable) {
                Log.e("ERROR", t.message.toString())
            }
        })
         return result
    }
}