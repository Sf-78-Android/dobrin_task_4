package com.training.countriesapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.training.countriesapp.adapter.DetailsAdapter.CountryDetailsViewHolder
import com.training.countriesapp.constants.Constants
import com.training.countriesapp.constants.Constants.AREA
import com.training.countriesapp.constants.Constants.AREA_TAG
import com.training.countriesapp.constants.Constants.CAPITAL
import com.training.countriesapp.constants.Constants.CAPITAL_TAG
import com.training.countriesapp.constants.Constants.CODE
import com.training.countriesapp.constants.Constants.CODE_TAG
import com.training.countriesapp.constants.Constants.COUNTRY_TAG
import com.training.countriesapp.constants.Constants.CURRENCY
import com.training.countriesapp.constants.Constants.CURRENCY_TAG
import com.training.countriesapp.constants.Constants.LANGUAGES
import com.training.countriesapp.constants.Constants.LANGUAGES_TAG
import com.training.countriesapp.constants.Constants.NAME
import com.training.countriesapp.constants.Constants.NATIVE_NAME
import com.training.countriesapp.constants.Constants.NATIVE_TAG
import com.training.countriesapp.constants.Constants.PHONE
import com.training.countriesapp.constants.Constants.PHONE_PREFIX
import com.training.countriesapp.constants.Constants.PHONE_TAG
import com.training.countriesapp.constants.Constants.POPULATION
import com.training.countriesapp.constants.Constants.POPULATION_TAG
import com.training.countriesapp.constants.Constants.REGION
import com.training.countriesapp.constants.Constants.REGION_TAG
import com.training.countriesapp.constants.Constants.SQUARE_KM
import com.training.countriesapp.databinding.CountryDetailsTvDesignBinding
import javax.inject.Inject

class DetailsAdapter @Inject constructor() : RecyclerView.Adapter<CountryDetailsViewHolder>() {
    var country = mutableListOf<String>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateCountry(countryDetails: List<String>) {
        country.clear()
        countryDetails.let { country.addAll(it) }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryDetailsViewHolder {
        val binding = CountryDetailsTvDesignBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryDetailsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryDetailsViewHolder, position: Int) {
        val item = country[position]
        item.let { holder.bind(it) }

    }

    override fun getItemCount() = country.size


    inner class CountryDetailsViewHolder(private val binding: CountryDetailsTvDesignBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(param: String) {
            val values = param.split(".")

            when (values[0]) {
                CODE -> {
                    Glide.with(binding.ivFlag)
                        .load(String.format(Constants.FLAGS_LINK, values[1].lowercase()))
                        .into(binding.ivFlag)
                    binding.ivFlag.visibility = View.VISIBLE
                    binding.tvValue.text = values[1]
                    binding.tvTag.text = CODE_TAG
                }
                NAME -> {
                    binding.tvValue.text = values[1]
                    binding.tvTag.text = COUNTRY_TAG
                }
                CAPITAL -> {
                    binding.tvValue.text = values[1]
                    binding.tvTag.text = CAPITAL_TAG
                }
                REGION -> {
                    binding.tvValue.text = values[1]
                    binding.tvTag.text = REGION_TAG
                }
                NATIVE_NAME -> {
                    binding.tvValue.text = values[1]
                    binding.tvTag.text = NATIVE_TAG
                }
                PHONE -> {
                    binding.tvValue.text = String.format(PHONE_PREFIX, values[1])
                    binding.tvTag.text = PHONE_TAG
                }
                LANGUAGES -> {
                    binding.tvValue.text = values[1]
                    binding.tvTag.text = LANGUAGES_TAG
                }
                CURRENCY -> {
                    binding.tvValue.text = values[1]
                    binding.tvTag.text = CURRENCY_TAG
                }
                AREA -> {
                    binding.tvValue.text = String.format(SQUARE_KM, values[1])
                    binding.tvTag.text = AREA_TAG
                }
                POPULATION -> {
                    binding.tvValue.text = values[1]
                    binding.tvTag.text = POPULATION_TAG
                }
            }
        }
    }
}
