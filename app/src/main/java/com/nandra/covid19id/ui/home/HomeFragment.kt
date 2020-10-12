package com.nandra.covid19id.ui.home

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.nandra.covid19id.CovidApplication
import com.nandra.covid19id.R
import com.nandra.covid19id.core.data.Resource
import com.nandra.covid19id.core.domain.model.GeneralCase
import com.nandra.covid19id.utils.ViewModelFactory
import com.nandra.covid19id.utils.setVisibilityGone
import com.nandra.covid19id.utils.setVisibilityVisible
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home_case_item.view.*
import javax.inject.Inject

class HomeFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    private val homeSharedViewModel: HomeSharedViewModel by activityViewModels { factory }
    private var snackBar: Snackbar? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observeData()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as CovidApplication).appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).apply {
            setSupportActionBar(fragment_home_toolbar)
        }
    }

    override fun onPause() {
        super.onPause()
        snackBar?.dismiss()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_fragment_toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.home_fragment_about_menu_item -> {
                showHomeDialogAlert()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun observeData() {
        homeSharedViewModel.globalCaseData.observe(viewLifecycleOwner) { resource ->
            if (resource != null) {
                when (resource) {
                    is Resource.Loading -> {
                        fragment_home_global_case_content_shimmer.setVisibilityVisible()
                        fragment_home_global_case_content.setVisibilityGone()
                    }
                    is Resource.Success -> {
                        fragment_home_global_case_content_shimmer.setVisibilityGone()
                        fragment_home_global_case_content?.let { view ->
                            bindGeneralCaseView(view, resource.data)
                            view.setVisibilityVisible()
                        }
                    }
                    is Resource.Error -> {
                        fragment_home_global_case_content_shimmer.setVisibilityVisible()
                        fragment_home_global_case_content.setVisibilityGone()
                        showErrorSnackBar()
                    }
                }
            }
        }

        homeSharedViewModel.indonesiaCaseData.observe(viewLifecycleOwner) { resource ->
            if (resource != null) {
                when (resource) {
                    is Resource.Loading -> {
                        fragment_home_indonesia_case_content_shimmer.setVisibilityVisible()
                        fragment_home_indonesia_case_content.setVisibilityGone()
                    }
                    is Resource.Success -> {
                        fragment_home_indonesia_case_content_shimmer.setVisibilityGone()
                        fragment_home_indonesia_case_content?.let { view ->
                            bindGeneralCaseView(view, resource.data)
                            view.setVisibilityVisible()
                        }
                    }
                    is Resource.Error -> {
                        fragment_home_indonesia_case_content_shimmer.setVisibilityVisible()
                        fragment_home_indonesia_case_content.setVisibilityGone()
                        showErrorSnackBar()
                    }
                }
            }
        }

        homeSharedViewModel.indonesiaProvinceCaseData.observe(viewLifecycleOwner) { resource ->
            if (resource != null) {
                when (resource) {
                    is Resource.Loading -> {
                        fragment_home_province_case_shimmer.setVisibilityVisible()
                        fragment_home_province_case_group.setVisibilityGone()
                    }
                    is Resource.Success -> {
                        fragment_home_province_case_shimmer.setVisibilityGone()
                        fragment_home_province_case_group.setVisibilityVisible()
                    }
                    is Resource.Error -> {
                        fragment_home_province_case_shimmer.setVisibilityVisible()
                        fragment_home_province_case_group.setVisibilityGone()
                        showErrorSnackBar()
                    }
                }
            }
        }

        homeSharedViewModel.otherCountriesCaseData.observe(viewLifecycleOwner) { resource ->
            if (resource != null) {
                when (resource) {
                    is Resource.Loading -> {
                        fragment_home_other_countries_case_shimmer.setVisibilityVisible()
                        fragment_home_other_countries_case_group.setVisibilityGone()
                    }
                    is Resource.Success -> {
                        fragment_home_other_countries_case_shimmer.setVisibilityGone()
                        fragment_home_other_countries_case_group.setVisibilityVisible()
                    }
                    is Resource.Error -> {
                        fragment_home_other_countries_case_shimmer.setVisibilityVisible()
                        fragment_home_other_countries_case_group.setVisibilityGone()
                        showErrorSnackBar()
                    }
                }
            }
        }
    }

    private fun bindGeneralCaseView(view: View, generalCase: GeneralCase?) {
        generalCase?.let {
            view.case_item_title.text = it.title
            view.case_item_total_infected_count_number.text = it.positiveCase.toString()
            view.case_item_total_death_count_number.text = it.deathCase.toString()
            view.case_item_total_recovered_count_number.text = it.curedCase.toString()
            val lastUpdateStringHolder = it.lastUpdateDate
            view.case_item_last_update.text = lastUpdateStringHolder
        }
    }

    private fun showHomeDialogAlert() {
        val alertDialog = AlertDialog.Builder(requireActivity()).create()
        alertDialog.apply {
            setTitle("COVID-19 ID v0.6.0")
            setMessage("Author: Nandra Saputra\nwww.github.com/nandrasaputra/Covid19ID")
            setIcon(R.drawable.img_covid_logo)
            setButton(AlertDialog.BUTTON_POSITIVE, "OK") { dialog, _ -> dialog?.dismiss() }
        }.show()
    }

    private fun setupView() {
        fragment_home_province_case_content.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_indonesiaProvinceDetailFragment)
        }
        fragment_home_other_countries_case_content.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_otherCountriesDetailFragment)
        }
    }

    private fun showErrorSnackBar() {
        snackBar = Snackbar.make(
            requireView(),
            "Error When Loading Data",
            Snackbar.LENGTH_INDEFINITE
        ).setAction("Retry") {
            homeSharedViewModel.retryAllFailed()
        }.setAnchorView(fragment_home_snackbar_anchor)
        snackBar?.show()
    }

}