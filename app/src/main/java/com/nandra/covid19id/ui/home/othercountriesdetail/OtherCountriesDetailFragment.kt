package com.nandra.covid19id.ui.home.othercountriesdetail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nandra.covid19id.CovidApplication
import com.nandra.covid19id.R
import com.nandra.covid19id.adapter.OtherCountriesDetailListAdapter
import com.nandra.covid19id.core.data.Resource
import com.nandra.covid19id.ui.home.HomeSharedViewModel
import com.nandra.covid19id.utils.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_other_countries_detail.*
import javax.inject.Inject

class OtherCountriesDetailFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    private val homeSharedViewModel: HomeSharedViewModel by activityViewModels { factory }
    private lateinit var otherCountriesDetailListAdapter: OtherCountriesDetailListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_other_countries_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observeViewModel()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as CovidApplication).appComponent.inject(this)
    }

    private fun observeViewModel() {
        homeSharedViewModel.otherCountriesCaseData.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    otherCountriesDetailListAdapter.submitList(resource.data)
                }
            }
        }
    }

    private fun setupView() {
        otherCountriesDetailListAdapter = OtherCountriesDetailListAdapter()
        fragment_other_countries_detail_recycler_view.apply {
            adapter = otherCountriesDetailListAdapter
            layoutManager = LinearLayoutManager(activity)
        }

        fragment_other_countries_detail_toolbar.apply {
            setNavigationIcon(R.drawable.ic_arrow_back_toolbar)
            setNavigationOnClickListener { activity?.onBackPressed() }
        }
    }
}
