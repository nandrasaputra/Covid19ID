package com.nandra.covid19id.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nandra.covid19id.R
import com.nandra.covid19id.adapter.IndonesiaProvinceDetailListAdapter.IndonesiaProvinceDetailViewHolder
import com.nandra.covid19id.network.response.model.Attributes
import kotlinx.android.synthetic.main.fragment_indonesia_province_detail_item.view.*

class IndonesiaProvinceDetailListAdapter
    : ListAdapter<Attributes, IndonesiaProvinceDetailViewHolder>(
    attributesDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndonesiaProvinceDetailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_indonesia_province_detail_item, parent, false)
        return IndonesiaProvinceDetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: IndonesiaProvinceDetailViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class IndonesiaProvinceDetailViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(attributes: Attributes) {
            val provinceName = "Provinsi ${attributes.namaProvinsi}"
            itemView.apply {
                province_detail_item_title.text = provinceName
                province_detail_item_total_infected_count_number.text = attributes.kasusPositif.toString()
                province_detail_item_total_recovered_count_number.text = attributes.kasusSembuh.toString()
                province_detail_item_total_death_count_number.text = attributes.kasusMeninggal.toString()
            }
        }
    }

    companion object {
        val attributesDiffUtil = object : DiffUtil.ItemCallback<Attributes>() {
            override fun areItemsTheSame(oldItem: Attributes, newItem: Attributes): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Attributes, newItem: Attributes): Boolean {
                return oldItem.fID == newItem.fID
            }
        }
    }

}