package com.nandra.covid19id.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nandra.covid19id.R
import com.nandra.covid19id.network.response.CountryResponse
import kotlinx.android.synthetic.main.fragment_other_countries_detail_item.view.*

class OtherCountriesDetailListAdapter
    : ListAdapter<CountryResponse, OtherCountriesDetailListAdapter.OtherCountriesDetailViewHolder>(otherCountriesDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OtherCountriesDetailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_other_countries_detail_item, parent, false)
        return OtherCountriesDetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: OtherCountriesDetailViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class OtherCountriesDetailViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(country: CountryResponse) {
            itemView.apply {
                other_countries_detail_item_title.text = country.countryName
                other_countries_detail_item_total_infected_count_number.text = country.totalCase.toString()
                other_countries_detail_item_total_recovered_count_number.text = country.totalRecovered.toString()
                other_countries_detail_item_total_death_count_number.text = country.totalDeath.toString()
            }

            Glide.with(itemView.context)
                .load(country.countryInfo.flagImagePath)
                .placeholder(R.color.shimmerColorOne)
                .into(itemView.other_countries_detail_item_flag)
        }
    }

    companion object {
        val otherCountriesDiffUtil = object : DiffUtil.ItemCallback<CountryResponse>() {
            override fun areItemsTheSame(oldItem: CountryResponse, newItem: CountryResponse): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: CountryResponse, newItem: CountryResponse): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}