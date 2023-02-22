package com.training.countriesapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.training.countriesapp.adapter.DetailsAdapter.CountryDetailsViewHolder
import com.training.countriesapp.constants.Constants
import com.training.countriesapp.constants.Constants.AREA_TAG
import com.training.countriesapp.constants.Constants.CAPITAL_TAG
import com.training.countriesapp.constants.Constants.CODE_TAG
import com.training.countriesapp.constants.Constants.COUNTRY_TAG
import com.training.countriesapp.constants.Constants.CURRENCY_TAG
import com.training.countriesapp.constants.Constants.EIGHT
import com.training.countriesapp.constants.Constants.FIVE
import com.training.countriesapp.constants.Constants.FOUR
import com.training.countriesapp.constants.Constants.LANGUAGES_TAG
import com.training.countriesapp.constants.Constants.NATIVE_TAG
import com.training.countriesapp.constants.Constants.NINE
import com.training.countriesapp.constants.Constants.ONE
import com.training.countriesapp.constants.Constants.PHONE_PREFIX
import com.training.countriesapp.constants.Constants.PHONE_TAG
import com.training.countriesapp.constants.Constants.POPULATION_TAG
import com.training.countriesapp.constants.Constants.REGION_TAG
import com.training.countriesapp.constants.Constants.SEVEN
import com.training.countriesapp.constants.Constants.SIX
import com.training.countriesapp.constants.Constants.SQUARE_KM
import com.training.countriesapp.constants.Constants.THREE
import com.training.countriesapp.constants.Constants.TWO
import com.training.countriesapp.constants.Constants.ZERO
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
                ZERO -> {
                    Glide.with(binding.ivFlag)
                        .load(String.format(Constants.FLAGS_LINK, values[1].lowercase()))
                        .into(binding.ivFlag)
                    binding.ivFlag.visibility = View.VISIBLE
                    binding.tvValue.text = values[1]
                    binding.tvTag.text = CODE_TAG
                }
                ONE -> {
                    binding.tvValue.text = values[1]
                    binding.tvTag.text = COUNTRY_TAG
                }
                TWO -> {
                    binding.tvValue.text = values[1]
                    binding.tvTag.text = CAPITAL_TAG
                }
                THREE -> {
                    binding.tvValue.text = values[1]
                    binding.tvTag.text = REGION_TAG
                }
                FOUR -> {
                    binding.tvValue.text = values[1]
                    binding.tvTag.text = NATIVE_TAG
                }
                FIVE -> {
                    binding.tvValue.text = String.format(PHONE_PREFIX, values[1])
                    binding.tvTag.text = PHONE_TAG
                }
                SIX -> {
                    binding.tvValue.text = values[1]
                    binding.tvTag.text = LANGUAGES_TAG
                }
                SEVEN -> {
                    binding.tvValue.text = values[1]
                    binding.tvTag.text = CURRENCY_TAG
                }
                EIGHT -> {
                    binding.tvValue.text = String.format(SQUARE_KM, values[1])
                    binding.tvTag.text = AREA_TAG
                }
                NINE -> {
                    binding.tvValue.text = values[1]
                    binding.tvTag.text = POPULATION_TAG
                }
            }
        }
    }
}
