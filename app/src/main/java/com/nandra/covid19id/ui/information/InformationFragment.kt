package com.nandra.covid19id.ui.information

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nandra.covid19id.CovidApplication
import com.nandra.covid19id.R
import com.nandra.covid19id.adapter.ContentListAdapter
import com.nandra.covid19id.core.data.Resource
import com.nandra.covid19id.utils.CustomItemDecorator
import com.nandra.covid19id.utils.ViewModelFactory
import com.nandra.covid19id.utils.setVisibilityGone
import com.nandra.covid19id.utils.setVisibilityVisible
import kotlinx.android.synthetic.main.fragment_information.*
import javax.inject.Inject

class InformationFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    private val informationViewModel: InformationViewModel by viewModels {factory}
    private lateinit var introductionListAdapter: ContentListAdapter
    private lateinit var otherListAdapter: ContentListAdapter
    private lateinit var lamanListAdapter: ContentListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_information, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as CovidApplication).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observeViewModel()
    }

    private fun observeViewModel() {
        informationViewModel.informationIntroductionData.observe(viewLifecycleOwner) { resource ->
            if (resource != null) {
                when (resource) {
                    is Resource.Loading -> {
                        fragment_information_introduction_shimmer.setVisibilityVisible()
                        fragment_information_introduction_group.setVisibilityGone()
                    }
                    is Resource.Success -> {
                        fragment_information_introduction_shimmer.setVisibilityGone()
                        introductionListAdapter.submitList(resource.data)
                        fragment_information_introduction_group.setVisibilityVisible()
                    }
                    is Resource.Error -> {
                        fragment_information_introduction_shimmer.setVisibilityVisible()
                        fragment_information_introduction_group.setVisibilityGone()
                    }
                }
            }
        }

        informationViewModel.informationOtherData.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    fragment_information_other_shimmer.setVisibilityVisible()
                    fragment_information_other_group.setVisibilityGone()
                }
                is Resource.Success -> {
                    fragment_information_other_shimmer.setVisibilityGone()
                    otherListAdapter.submitList(resource.data)
                    fragment_information_other_group.setVisibilityVisible()
                }
                is Resource.Error -> {
                    fragment_information_other_shimmer.setVisibilityVisible()
                    fragment_information_other_group.setVisibilityGone()
                }
            }
        }

        informationViewModel.informationWebPagesData.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    fragment_information_laman_shimmer.setVisibilityVisible()
                    fragment_information_laman_group.setVisibilityGone()
                }
                is Resource.Success -> {
                    fragment_information_laman_shimmer.setVisibilityGone()
                    lamanListAdapter.submitList(resource.data)
                    fragment_information_laman_group.setVisibilityVisible()
                }
                is Resource.Error -> {
                    fragment_information_laman_shimmer.setVisibilityVisible()
                    fragment_information_laman_group.setVisibilityGone()
                }
            }
        }
    }

    private fun setupView() {
        introductionListAdapter = ContentListAdapter()
        otherListAdapter = ContentListAdapter()
        lamanListAdapter = ContentListAdapter()

        fragment_information_introduction_recycler_view.apply {
            adapter = introductionListAdapter
            addItemDecoration(CustomItemDecorator(16))
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }

        fragment_information_other_recycler_view.apply {
            adapter = otherListAdapter
            addItemDecoration(CustomItemDecorator(16))
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }

        fragment_information_laman_recycler_view.apply {
            adapter = lamanListAdapter
            addItemDecoration(CustomItemDecorator(16))
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
    }
}
