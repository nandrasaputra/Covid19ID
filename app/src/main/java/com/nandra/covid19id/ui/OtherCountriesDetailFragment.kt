package com.nandra.covid19id.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.nandra.covid19id.R
import com.nandra.covid19id.adapter.OtherCountriesDetailListAdapter
import com.nandra.covid19id.network.response.CountryResponse
import com.nandra.covid19id.viewmodel.SharedViewModel
import com.nandra.covid19id.viewmodel.SharedViewModel.Companion.DataLoadState
import kotlinx.android.synthetic.main.fragment_other_countries_detail.*

class OtherCountriesDetailFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var otherCountriesDetailListAdapter: OtherCountriesDetailListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_other_countries_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observeViewModel()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fragment_other_countries_detail_recycler_view.apply {
            adapter = otherCountriesDetailListAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun observeViewModel() {
        sharedViewModel.homeOtherCountriesCoronaDataLoadState.observe(viewLifecycleOwner, Observer {
            handleOtherCountriesCoronaDataLoadState(it)
        })
    }

    private fun setupView() {
        fragment_other_countries_detail_toolbar.apply {
            setNavigationIcon(R.drawable.ic_arrow_back_toolbar)
            setNavigationOnClickListener { activity?.onBackPressed() }
        }
        otherCountriesDetailListAdapter = OtherCountriesDetailListAdapter()
    }

    private fun handleOtherCountriesCoronaDataLoadState(state: DataLoadState) {
        when(state) {
            is DataLoadState.Loaded -> {
                otherCountriesDetailListAdapter.submitList(state.data as List<CountryResponse>)
            }
            DataLoadState.Unloaded -> {}
            DataLoadState.Loading -> {}
            is DataLoadState.Error -> {}
        }
    }
}