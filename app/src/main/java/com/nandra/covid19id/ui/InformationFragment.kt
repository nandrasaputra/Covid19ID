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
import com.nandra.covid19id.adapter.ContentListAdapter
import com.nandra.covid19id.model.Content
import com.nandra.covid19id.utils.Utility
import com.nandra.covid19id.viewmodel.SharedViewModel
import com.nandra.covid19id.viewmodel.SharedViewModel.Companion.DataLoadState
import kotlinx.android.synthetic.main.fragment_information.*
import kotlinx.coroutines.Dispatchers

class InformationFragment : Fragment() {

    private lateinit var introductionListAdapter: ContentListAdapter
    private lateinit var otherListAdapter: ContentListAdapter
    private lateinit var lamanListAdapter: ContentListAdapter
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_information, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observeViewModel()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fragment_information_introduction_recycler_view.apply {
            adapter = introductionListAdapter
            addItemDecoration(Utility.CustomItemDecorator(16))
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }

        fragment_information_other_recycler_view.apply {
            adapter = otherListAdapter
            addItemDecoration(Utility.CustomItemDecorator(16))
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }

        fragment_information_laman_recycler_view.apply {
            adapter = lamanListAdapter
            addItemDecoration(Utility.CustomItemDecorator(16))
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun observeViewModel() {
        sharedViewModel.informationIntroductionDataLoadState.observe(viewLifecycleOwner, Observer {
            handleIntroductionDataLoadState(it)
        })
        sharedViewModel.informationOtherDataLoadState.observe(viewLifecycleOwner, Observer {
            handleOtherDataLoadState(it)
        })
        sharedViewModel.informationLamanDataLoadState.observe(viewLifecycleOwner, Observer {
            handleLamanDataLoadState(it)
        })
    }

    private fun setupView() {
        introductionListAdapter = ContentListAdapter()
        otherListAdapter = ContentListAdapter()
        lamanListAdapter = ContentListAdapter()
    }

    private fun handleIntroductionDataLoadState(state: DataLoadState) {
        when(state) {
            is DataLoadState.Loaded -> {
                introductionListAdapter.submitList(state.data as List<Content>)
                fragment_information_introduction_group.visibility = View.VISIBLE
                fragment_information_introduction_shimmer.visibility = View.GONE
            }
            DataLoadState.Unloaded -> {
                sharedViewModel.getInformationIntroductionList(Dispatchers.IO)
            }
            DataLoadState.Loading -> {
                if (fragment_information_introduction_group.visibility == View.VISIBLE) {
                    fragment_information_introduction_group.visibility = View.GONE
                }
                fragment_information_introduction_shimmer.visibility = View.VISIBLE
            }
            is DataLoadState.Error -> {
                fragment_information_introduction_shimmer.visibility = View.GONE
            }
        }
    }

    private fun handleOtherDataLoadState(state: DataLoadState) {
        when(state) {
            is DataLoadState.Loaded -> {
                otherListAdapter.submitList(state.data as List<Content>)
                fragment_information_other_group.visibility = View.VISIBLE
                fragment_information_other_shimmer.visibility = View.GONE
            }
            DataLoadState.Unloaded -> {
                sharedViewModel.getInformationOtherList(Dispatchers.IO)
            }
            DataLoadState.Loading -> {
                if (fragment_information_other_group.visibility == View.VISIBLE) {
                    fragment_information_other_group.visibility = View.GONE
                }
                fragment_information_other_shimmer.visibility = View.VISIBLE
            }
            is DataLoadState.Error -> {
                fragment_information_other_shimmer.visibility = View.GONE
            }
        }
    }

    private fun handleLamanDataLoadState(state: DataLoadState) {
        when(state) {
            is DataLoadState.Loaded -> {
                lamanListAdapter.submitList(state.data as List<Content>)
                fragment_information_laman_group.visibility = View.VISIBLE
                fragment_information_laman_shimmer.visibility = View.GONE
            }
            DataLoadState.Unloaded -> {
                sharedViewModel.getInformationLamanList(Dispatchers.IO)
            }
            DataLoadState.Loading -> {
                if (fragment_information_other_group.visibility == View.VISIBLE) {
                    fragment_information_other_group.visibility = View.GONE
                }
                fragment_information_laman_shimmer.visibility = View.VISIBLE
            }
            is DataLoadState.Error -> {
                fragment_information_laman_shimmer.visibility = View.GONE
            }
        }
    }
}
