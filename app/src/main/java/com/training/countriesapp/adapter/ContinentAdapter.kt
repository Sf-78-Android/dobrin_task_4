package com.training.countriesapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.training.countriesapp.ContinentsListQuery
import com.training.countriesapp.R
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

    override fun getItemCount() = dataset?.size ?: 0

    inner class ContinentViewHolder(private val binding: ContinentViewDesignBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(continent: ContinentsListQuery.Continent) {
            binding.tvContinentName.text = continent.name
            binding.tvTotalCountries.text = continent.countries.size.toString()
            when (continent.name) {
                "Africa" -> binding.ivContinent.setImageResource(R.drawable.africa)
                "Antarctica" -> binding.ivContinent.setImageResource(R.drawable.antarctica)
                "Asia" -> binding.ivContinent.setImageResource(R.drawable.asia)
                "Europe" -> binding.ivContinent.setImageResource(R.drawable.europe)
                "North America" -> binding.ivContinent.setImageResource(R.drawable.north_america)
                "South America" -> binding.ivContinent.setImageResource(R.drawable.south_america)
                "Oceania" -> binding.ivContinent.setImageResource(R.drawable.australia)
            }
        }
    }
}