package com.nandra.covid19id.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.nandra.covid19id.R
import com.nandra.covid19id.core.domain.model.InformationContent
import com.nandra.covid19id.ui.WebViewActivity
import com.nandra.covid19id.utils.Constant
import kotlinx.android.synthetic.main.fragment_information_item.view.*

class ContentListAdapter : ListAdapter<InformationContent, ContentListAdapter.ContentViewHolder>(contentDiffUtil) {

    private val firebaseStorage = FirebaseStorage.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_information_item, parent, false)
        return ContentViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ContentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(informationContent: InformationContent) {

            itemView.apply {
                fragment_information_item_title.text = informationContent.title
                fragment_information_item_description.text = informationContent.description
            }

            Glide.with(itemView.context)
                .load(firebaseStorage.getReferenceFromUrl(informationContent.image_path))
                .placeholder(R.color.shimmerColorTwo)
                .into(itemView.fragment_information_item_image)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, WebViewActivity::class.java).apply {
                    putExtra(Constant.EXTRA_WEB_SITE, informationContent.website_path)
                    putExtra(Constant.EXTRA_WEB_TITLE, informationContent.title)
                }
                itemView.context.startActivity(intent)
            }
        }
    }

    companion object {
        val contentDiffUtil = object : DiffUtil.ItemCallback<InformationContent>() {
            override fun areItemsTheSame(oldItem: InformationContent, newItem: InformationContent): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: InformationContent, newItem: InformationContent): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}