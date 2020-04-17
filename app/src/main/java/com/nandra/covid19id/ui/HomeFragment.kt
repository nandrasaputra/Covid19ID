package com.nandra.covid19id.ui

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.nandra.covid19id.R
import com.nandra.covid19id.network.response.CountryResponse
import com.nandra.covid19id.viewmodel.SharedViewModel
import com.nandra.covid19id.viewmodel.SharedViewModel.Companion.DataLoadState
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home_case_item.view.*
import kotlinx.coroutines.Dispatchers
import java.text.SimpleDateFormat
import java.util.*

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

    private fun observeViewModel() {
        sharedViewModel.homeGlobalCoronaDataLoadState.observe(viewLifecycleOwner, Observer {
            handleGlobalCoronaDataLoadState(it)
        })
        sharedViewModel.homeIndonesiaCoronaDataLoadState.observe(viewLifecycleOwner, Observer {
            handleIndonesiaCoronaDataLoadState(it)
        })
        sharedViewModel.homeIndonesiaProvinceCoronaDataLoadState.observe(viewLifecycleOwner, Observer {
            handleIndonesiaProvinceCoronaDataLoadState(it)
        })
    }

    private fun handleGlobalCoronaDataLoadState(state: DataLoadState) {
        when(state) {
            is DataLoadState.Loaded -> {
                val data = state.data as CountryResponse
                var lastUpdate: String? = null
                try {
                    lastUpdate = convertMillisecondToStringDate(data.updateDate)
                } catch (exception: Exception) { }

                fragment_home_global_case.apply {
                    case_item_title.text = "Global"
                    case_item_total_infected_count_number.text = data.totalCase.toString()
                    case_item_total_death_count_number.text = data.totalDeath.toString()
                    case_item_total_recovered_count_number.text = data.totalRecovered.toString()
                    if (lastUpdate != null) {
                        case_item_last_update.visibility = View.VISIBLE
                        case_item_last_update.text = "Update: $lastUpdate"
                    } else {
                        case_item_last_update.visibility = View.GONE
                    }
                }
                fragment_home_headline_case_shimmer.visibility = View.GONE
                fragment_home_global_case_shimmer.visibility = View.GONE
                fragment_home_headline_case.visibility = View.VISIBLE
                fragment_home_global_case.visibility = View.VISIBLE
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
                var lastUpdate: String? = null
                try {
                    lastUpdate = convertMillisecondToStringDate(data.updateDate)
                } catch (exception: Exception) { }

                fragment_home_indonesia_case.apply {
                    case_item_title.text = "Indonesia"
                    case_item_total_infected_count_number.text = data.totalCase.toString()
                    case_item_total_death_count_number.text = data.totalDeath.toString()
                    case_item_total_recovered_count_number.text = data.totalRecovered.toString()
                    if (lastUpdate != null) {
                        case_item_last_update.visibility = View.VISIBLE
                        case_item_last_update.text = "Update : $lastUpdate"
                    } else {
                        case_item_last_update.visibility = View.GONE
                    }
                }
                fragment_home_indonesia_case_shimmer.visibility = View.GONE
                fragment_home_indonesia_case.visibility = View.VISIBLE

                //TODO: FIX THIS LATER
                sharedViewModel.getHomeIndonesiaProvinceCoronaList(Dispatchers.IO)
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

    private fun handleIndonesiaProvinceCoronaDataLoadState(state: DataLoadState) {
        when(state) {
            is DataLoadState.Loaded -> {
                fragment_home_case_province_group.visibility = View.VISIBLE
            }
            DataLoadState.Unloaded -> {

            }
            DataLoadState.Loading -> {

            }
            is DataLoadState.Error -> {
                if (isConnectedToInternet()) {
                    sharedViewModel.getHomeIndonesiaProvinceCoronaList(Dispatchers.IO)
                }
            }
        }
    }

    private fun setupView() {
        fragment_home_case_province.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_indonesiaProvinceDetailFragment)
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

    private fun convertMillisecondToStringDate(millisecond: Long) : String {
        val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm")
        return formatter.format(Date(millisecond))
    }

}