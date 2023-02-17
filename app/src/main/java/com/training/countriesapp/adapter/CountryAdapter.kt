package com.training.countriesapp.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.training.countriesapp.CountryListQuery
import com.training.countriesapp.R
import com.training.countriesapp.constants.Constants.FLAGS_LINK
import com.training.countriesapp.databinding.CardViewDesignBinding
import com.training.countriesapp.view.CountryDetailsView

class CountryAdapter(
    private val dataset: List<CountryListQuery.Country>?
) :
    RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    private var countryList: MutableList<CountryListQuery.Country>? =
        dataset as MutableList<CountryListQuery.Country>?


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

        Glide.with(holder.itemView)
            .load(String.format(FLAGS_LINK, item?.code?.lowercase()))
            .into(holder.binding.ivFlag)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, CountryDetailsView::class.java)
            intent.putExtra(holder.itemView.context.getString(R.string.code), item?.code)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = dataset?.size ?: 0

    inner class CountryViewHolder(val binding: CardViewDesignBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(country: CountryListQuery.Country) {
            binding.tvCountryName.text = country.name
            binding.tvCountryCapital.text = country.capital
            binding.tvCountryRegion.text = country.continent.name
        }
    }
}