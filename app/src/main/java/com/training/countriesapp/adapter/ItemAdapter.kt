package com.training.countriesapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.training.countriesapp.CountryListQuery
import com.training.countriesapp.R
import com.training.countriesapp.activities.CountryDetailsActivity
import com.training.countriesapp.constants.Constants.FLAGS_LINK

class ItemAdapter(private val context: Context, private val dataset: List<CountryListQuery.Country>?) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvCountryName: TextView = view.findViewById(R.id.tvCountryName)
        val tvCountryCapital: TextView = view.findViewById(R.id.tvCountryCapital)
        val tvCountryRegion: TextView = view.findViewById(R.id.tvCountryRegion)
        val ivFlag : ImageView = view.findViewById(R.id.ivFlag)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.card_view_design, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset?.get(position)
        holder.tvCountryName.text = item?.name
        holder.tvCountryCapital.text = item?.capital
        holder.tvCountryRegion.text = item?.continent?.name

        Glide.with(holder.itemView)
            .load(String.format(FLAGS_LINK,item?.code?.lowercase()))
            .into(holder.ivFlag)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, CountryDetailsActivity::class.java)
            intent.putExtra("code",item?.code)

            context.startActivity(intent)
        }
    }

    override fun getItemCount() = dataset?.size ?: 0
}