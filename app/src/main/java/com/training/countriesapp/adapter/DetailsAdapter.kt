package com.training.countriesapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.training.countriesapp.adapter.DetailsAdapter.CountryDetailsViewHolder
import com.training.countriesapp.constants.Constants.AREA_TAG
import com.training.countriesapp.constants.Constants.CAPITAL_TAG
import com.training.countriesapp.constants.Constants.CODE_TAG
import com.training.countriesapp.constants.Constants.COUNTRY_TAG
import com.training.countriesapp.constants.Constants.CURRENCY_TAG
import com.training.countriesapp.constants.Constants.LANGUAGES_TAG
import com.training.countriesapp.constants.Constants.NATIVE_TAG
import com.training.countriesapp.constants.Constants.PHONE_PREFIX
import com.training.countriesapp.constants.Constants.PHONE_TAG
import com.training.countriesapp.constants.Constants.POPULATION_TAG
import com.training.countriesapp.constants.Constants.REGION_TAG
import com.training.countriesapp.constants.Constants.SQUARE_KM
import com.training.countriesapp.databinding.CountryDetailsTvDesignBinding

class DetailsAdapter(
    var country: MutableList<String>

) : RecyclerView.Adapter<CountryDetailsViewHolder>() {

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
        /*
               Glide.with(holder.itemView)
                   .load(String.format(Constants.FLAGS_LINK, country[0].split(".")[1].lowercase()))
                   .into(holder.binding.ivFlag)

         */


    }

    override fun getItemCount() = country.size


    inner class CountryDetailsViewHolder(private val binding: CountryDetailsTvDesignBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(param: String) {
            val values = param.split(".")
            when (values[0]) {
                "0" -> {
                    binding.tvValue.text = values[1]
                    binding.tvTag.text = CODE_TAG
                }
                "1" -> {
                    binding.tvValue.text = values[1]
                    binding.tvTag.text = COUNTRY_TAG
                }
                "2" -> {
                    binding.tvValue.text = values[1]
                    binding.tvTag.text = CAPITAL_TAG
                }
                "3" -> {
                    binding.tvValue.text = values[1]
                    binding.tvTag.text = REGION_TAG
                }
                "4" -> {
                    binding.tvValue.text = values[1]
                    binding.tvTag.text = NATIVE_TAG
                }
                "5" -> {
                    binding.tvValue.text = String.format(PHONE_PREFIX, values[1])
                    binding.tvTag.text = PHONE_TAG
                }
                "6" -> {
                    binding.tvValue.text = values[1]
                    binding.tvTag.text = LANGUAGES_TAG
                }
                "7" -> {
                    binding.tvValue.text = values[1]
                    binding.tvTag.text = CURRENCY_TAG
                }
                "8" -> {
                    binding.tvValue.text = String.format(SQUARE_KM, values[1])
                    binding.tvTag.text = AREA_TAG
                }
                "9" -> {
                    binding.tvValue.text = values[1]
                    binding.tvTag.text = POPULATION_TAG
                }
                else -> {

                }
            }
        }
    }
}
