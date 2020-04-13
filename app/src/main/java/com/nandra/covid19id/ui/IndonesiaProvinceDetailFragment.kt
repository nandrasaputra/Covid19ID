package com.nandra.covid19id.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.nandra.covid19id.R
import com.nandra.covid19id.adapter.IndonesiaProvinceDetailListAdapter
import com.nandra.covid19id.network.response.model.Attributes
import com.nandra.covid19id.viewmodel.SharedViewModel
import com.nandra.covid19id.viewmodel.SharedViewModel.Companion.DataLoadState
import kotlinx.android.synthetic.main.fragment_indonesia_province_detail.*

class IndonesiaProvinceDetailFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var indonesiaProvinceDetailListAdapter: IndonesiaProvinceDetailListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_indonesia_province_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observeViewModel()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fragment_indonesia_province_detail_recycler_view.apply {
            adapter = indonesiaProvinceDetailListAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun observeViewModel() {
        sharedViewModel.homeIndonesiaProvinceCoronaDataLoadState.observe(viewLifecycleOwner, Observer {
            handleIndonesiaProvinceCoronaDataLoadState(it)
        })
    }

    private fun setupView() {
        fragment_indonesia_province_detail_toolbar.setNavigationIcon(R.drawable.ic_arrow_back_toolbar)
        fragment_indonesia_province_detail_toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
        indonesiaProvinceDetailListAdapter = IndonesiaProvinceDetailListAdapter()
    }

    private fun handleIndonesiaProvinceCoronaDataLoadState(state: DataLoadState) {
        when(state) {
            is DataLoadState.Loaded -> {
                indonesiaProvinceDetailListAdapter.submitList(state.data as List<Attributes>)
                Log.d("COVIDNANDRA", state.data.toString())
            }
            DataLoadState.Unloaded -> {

            }
            DataLoadState.Loading -> {

            }
            is DataLoadState.Error -> {

            }
        }
    }
}