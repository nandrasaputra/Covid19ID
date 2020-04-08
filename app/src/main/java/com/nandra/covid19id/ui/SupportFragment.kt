package com.nandra.covid19id.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nandra.covid19id.R
import kotlinx.android.synthetic.main.fragment_support.*

class SupportFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_support, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragment_support_card_call_center.setOnClickListener {
            val openDialerIntent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:119")
            }
            activity?.startActivity(openDialerIntent)
        }

        fragment_support_card_nearby_hospital.setOnClickListener {
            val webPage = Uri.parse("https://bnpb-inacovid19.hub.arcgis.com/app/d2595853cbc849ab9e9a790b4345ba38")
            val intent = Intent(Intent.ACTION_VIEW, webPage)
            activity?.run {
                if (intent.resolveActivity(this.packageManager) != null) {
                    startActivity(intent)
                }
            }
        }
    }
}