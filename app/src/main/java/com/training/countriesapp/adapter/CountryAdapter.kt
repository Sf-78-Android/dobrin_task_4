package com.training.countriesapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.training.countriesapp.CountryListQuery
import com.training.countriesapp.constants.Constants.FLAGS_LINK
import com.training.countriesapp.databinding.CardViewDesignBinding
import com.training.countriesapp.view.CountryListFragmentDirections
import javax.inject.Inject

class CountryAdapter @Inject constructor() :
    RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {
    private val dataset = mutableListOf<CountryListQuery.Country>()
    private var countryList: MutableList<CountryListQuery.Country>? = dataset


    @SuppressLint("NotifyDataSetChanged")
    fun updateCountries(newCountries: List<CountryListQuery.Country>?) {
        countryList?.clear()
        countryList?.addAll(newCountries as MutableList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding = CardViewDesignBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val item = countryList?.get(position)
        item?.let { holder.bind(it) }
    }

    override fun getItemCount() = dataset.size

    inner class CountryViewHolder(private val binding: CardViewDesignBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(country: CountryListQuery.Country) {

            Glide.with(binding.ivFlag).load(String.format(FLAGS_LINK, country.code.lowercase()))
                .into(binding.ivFlag)
            binding.root.setOnClickListener {
                binding.root.findNavController()
                    .navigate(
                        CountryListFragmentDirections.actionCountryListFragmentToCountryDetailsFragment(
                            country.code
                        )
                    )
            }
            binding.tvCountryName.text = country.name
            binding.tvCountryCapital.text = country.capital
            binding.tvCountryRegion.text = country.continent.name
        }
    }
}