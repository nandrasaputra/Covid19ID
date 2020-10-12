package com.nandra.covid19id.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nandra.covid19id.R
import com.nandra.covid19id.core.domain.model.CountryCase
import kotlinx.android.synthetic.main.fragment_other_countries_detail_item.view.*

class OtherCountriesDetailListAdapter
    : ListAdapter<CountryCase, OtherCountriesDetailListAdapter.OtherCountriesDetailViewHolder>(otherCountriesDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OtherCountriesDetailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_other_countries_detail_item, parent, false)
        return OtherCountriesDetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: OtherCountriesDetailViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class OtherCountriesDetailViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(country: CountryCase) {
            itemView.apply {
                other_countries_detail_item_title.text = country.title
                other_countries_detail_item_total_infected_count_number.text = country.positiveCase.toString()
                other_countries_detail_item_total_recovered_count_number.text = country.curedCase.toString()
                other_countries_detail_item_total_death_count_number.text = country.deathCase.toString()
            }

            Glide.with(itemView.context)
                .load(country.countryFlagImageUrl)
                .placeholder(R.color.shimmerColorOne)
                .into(itemView.other_countries_detail_item_flag)
        }
    }

    companion object {
        val otherCountriesDiffUtil = object : DiffUtil.ItemCallback<CountryCase>() {
            override fun areItemsTheSame(oldItem: CountryCase, newItem: CountryCase): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: CountryCase, newItem: CountryCase): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}