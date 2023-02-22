package com.training.countriesapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.training.countriesapp.ContinentsListQuery
import com.training.countriesapp.R
import com.training.countriesapp.constants.Constants.AFRICA
import com.training.countriesapp.constants.Constants.ANTARCTICA
import com.training.countriesapp.constants.Constants.ASIA
import com.training.countriesapp.constants.Constants.EUROPE
import com.training.countriesapp.constants.Constants.NORTH_AMERICA
import com.training.countriesapp.constants.Constants.OCEANIA
import com.training.countriesapp.constants.Constants.SOUTH_AMERICA
import com.training.countriesapp.databinding.ContinentViewDesignBinding
import javax.inject.Inject

class ContinentAdapter @Inject constructor() :
    RecyclerView.Adapter<ContinentAdapter.ContinentViewHolder>() {
    private val dataset = mutableListOf<ContinentsListQuery.Continent>()
    private var countryList: MutableList<ContinentsListQuery.Continent>? = dataset

    @SuppressLint("NotifyDataSetChanged")
    fun updateContinents(newContinents: List<ContinentsListQuery.Continent>?) {
        countryList?.clear()
        countryList?.addAll(newContinents as MutableList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContinentAdapter.ContinentViewHolder {
        val binding = ContinentViewDesignBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ContinentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContinentAdapter.ContinentViewHolder, position: Int) {
        val item = countryList?.get(position)
        item?.let { holder.bind(it) }

        holder.itemView.setOnClickListener {

        }
    }

    override fun getItemCount() = dataset.size

    inner class ContinentViewHolder(private val binding: ContinentViewDesignBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(continent: ContinentsListQuery.Continent) {
            binding.tvContinentName.text = continent.name
            binding.tvTotalCountries.text = continent.countries.size.toString()
            when (continent.name) {
                AFRICA -> binding.ivContinent.setImageResource(R.drawable.africa)
                ANTARCTICA -> binding.ivContinent.setImageResource(R.drawable.antarctica)
                ASIA -> binding.ivContinent.setImageResource(R.drawable.asia)
                EUROPE -> binding.ivContinent.setImageResource(R.drawable.europe)
                NORTH_AMERICA -> binding.ivContinent.setImageResource(R.drawable.north_america)
                SOUTH_AMERICA -> binding.ivContinent.setImageResource(R.drawable.south_america)
                OCEANIA -> binding.ivContinent.setImageResource(R.drawable.australia)
            }
        }
    }
}