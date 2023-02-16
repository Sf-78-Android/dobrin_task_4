package com.training.countriesapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.training.countriesapp.CountryDetailsQuery
import com.training.countriesapp.adapter.DetailsAdapter.CountryDetailsViewHolder
import com.training.countriesapp.constants.Constants
import com.training.countriesapp.databinding.CountryViewDetailsDesignBinding

class DetailsAdapter(
    var country: MutableList< CountryDetailsQuery.Country>?

    ) : RecyclerView.Adapter<CountryDetailsViewHolder>() {

    fun updateCountry(countryDetails : List< CountryDetailsQuery.Country>?){
        country?.clear()
        countryDetails?.let { country?.addAll(it) }
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryDetailsViewHolder {
        val binding = CountryViewDetailsDesignBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryDetailsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryDetailsViewHolder, position: Int) {
        val item = country?.get(position)
        item?.let { holder.bind(it) }

        Glide.with(holder.itemView)
            .load(String.format(Constants.FLAGS_LINK, item?.code?.lowercase()))
            .into(holder.binding.ivFlag)

    }

    override fun getItemCount() = country?.size ?: 0


    inner class CountryDetailsViewHolder(val binding: CountryViewDetailsDesignBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(country: CountryDetailsQuery.Country) {
            binding.tvCountryName.text = country.name
            binding.tvCountryCapital.text = country.capital
            binding.tvCountryRegion.text = country.continent.name
            binding.tvPhonePrefix.text =
                String.format(Constants.PHONE_PREFIX, country.phone)
            binding.tvCurrency.text = country.currency
            binding.tvLanguages.text = country.languages.joinToString { ", " }
            binding.tvNativeName.text = country.native
            binding.tvPopulation.text = "Not yet"

        }
    }
}
