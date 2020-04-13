package com.nandra.covid19id.ui

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.nandra.covid19id.R
import com.nandra.covid19id.network.response.CountryResponse
import com.nandra.covid19id.viewmodel.SharedViewModel
import com.nandra.covid19id.viewmodel.SharedViewModel.Companion.DataLoadState
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home_case_item.view.*
import kotlinx.coroutines.Dispatchers

class HomeFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var currentToast: Toast? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observeViewModel()
    }

    private fun setupView() {

    }

    private fun observeViewModel() {
        sharedViewModel.homeGlobalCoronaDataLoadState.observe(viewLifecycleOwner, Observer {
            handleGlobalCoronaDataLoadState(it)
        })
        sharedViewModel.homeIndonesiaCoronaDataLoadState.observe(viewLifecycleOwner, Observer {
            handleIndonesiaCoronaDataLoadState(it)
        })
    }

    private fun handleGlobalCoronaDataLoadState(state: DataLoadState) {
        when(state) {
            is DataLoadState.Loaded -> {
                val data = state.data as CountryResponse
                fragment_home_global_case.apply {
                    case_item_title.text = "Global"
                    case_item_total_infected_count_number.text = data.totalCase.toString()
                    case_item_total_death_count_number.text = data.totalDeath.toString()
                    case_item_total_recovered_count_number.text = data.totalRecovered.toString()
                }
                fragment_home_headline_case_shimmer.visibility = View.GONE
                fragment_home_global_case_shimmer.visibility = View.GONE
                fragment_home_headline_case.visibility = View.VISIBLE
                fragment_home_global_case.visibility = View.VISIBLE
                Log.d("COVIDNANDRA", "GLOBALLLL = ${state.data}")
            }
            DataLoadState.Unloaded -> {
                sharedViewModel.getHomeGlobalCoronaList(Dispatchers.IO)
            }
            DataLoadState.Loading -> {
                fragment_home_headline_case_shimmer.visibility = View.VISIBLE
                fragment_home_global_case_shimmer.visibility = View.VISIBLE
            }
            is DataLoadState.Error -> {
                if (isConnectedToInternet()) {
                    sharedViewModel.getHomeGlobalCoronaList(Dispatchers.IO)
                } else {
                    showErrorToast(state.errorMessage)
                }
            }
        }
    }

    private fun handleIndonesiaCoronaDataLoadState(state: DataLoadState) {
        when(state) {
            is DataLoadState.Loaded -> {
                val data = state.data as CountryResponse
                fragment_home_indonesia_case.apply {
                    case_item_title.text = "Indonesia"
                    case_item_total_infected_count_number.text = data.totalCase.toString()
                    case_item_total_death_count_number.text = data.totalDeath.toString()
                    case_item_total_recovered_count_number.text = data.totalRecovered.toString()
                }
                fragment_home_indonesia_case_shimmer.visibility = View.GONE
                fragment_home_indonesia_case.visibility = View.VISIBLE
                Log.d("COVIDNANDRA", "INDONESAH = ${state.data}")
            }
            DataLoadState.Unloaded -> {
                sharedViewModel.getHomeIndonesiaCoronaList(Dispatchers.IO)
            }
            DataLoadState.Loading -> {
                fragment_home_indonesia_case_shimmer.visibility = View.VISIBLE
            }
            is DataLoadState.Error -> {
                if (isConnectedToInternet()) {
                    sharedViewModel.getHomeIndonesiaCoronaList(Dispatchers.IO)
                } else {
                    showErrorToast(state.errorMessage)
                }
            }
        }
    }

    private fun showErrorToast(errorMessage: String) {
        currentToast?.run {
            cancel()
        }
        currentToast = Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT)
        currentToast?.show()
    }

    @Suppress("DEPRECATION")
    private fun isConnectedToInternet() : Boolean {
        val connectivityManager = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

}