package com.nandra.covid19id.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nandra.covid19id.R
import com.nandra.covid19id.adapter.IndonesiaProvinceDetailListAdapter.IndonesiaProvinceDetailViewHolder
import com.nandra.covid19id.core.domain.model.IndonesiaProvinceCase
import kotlinx.android.synthetic.main.fragment_indonesia_province_detail_item.view.*

class IndonesiaProvinceDetailListAdapter
    : ListAdapter<IndonesiaProvinceCase, IndonesiaProvinceDetailViewHolder>(
    attributesDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndonesiaProvinceDetailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_indonesia_province_detail_item, parent, false)
        return IndonesiaProvinceDetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: IndonesiaProvinceDetailViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class IndonesiaProvinceDetailViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(case: IndonesiaProvinceCase) {
            val provinceName = "Provinsi ${case.title}"
            itemView.apply {
                province_detail_item_title.text = provinceName
                province_detail_item_total_infected_count_number.text = case.positiveCase.toString()
                province_detail_item_total_recovered_count_number.text = case.curedCase.toString()
                province_detail_item_total_death_count_number.text = case.deathCase.toString()
            }
        }
    }

    companion object {
        val attributesDiffUtil = object : DiffUtil.ItemCallback<IndonesiaProvinceCase>() {
            override fun areItemsTheSame(oldItem: IndonesiaProvinceCase, newItem: IndonesiaProvinceCase): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: IndonesiaProvinceCase, newItem: IndonesiaProvinceCase): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}