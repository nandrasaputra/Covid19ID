package com.nandra.covid19id.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.nandra.covid19id.model.Content
import com.nandra.covid19id.repository.CovidRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class SharedViewModel(app: Application) : AndroidViewModel(app) {

    private val covidRepository = CovidRepository(app)

    val informationIntroductionDataLoadState: LiveData<DataLoadState>
        get() = _informationIntroductionDataLoadState
    private val _informationIntroductionDataLoadState = MutableLiveData<DataLoadState>(DataLoadState.Unloaded)

    val informationOtherDataLoadState: LiveData<DataLoadState>
        get() = _informationOtherDataLoadState
    private val _informationOtherDataLoadState = MutableLiveData<DataLoadState>(DataLoadState.Unloaded)

    val informationLamanDataLoadState: LiveData<DataLoadState>
        get() = _informationLamanDataLoadState
    private val _informationLamanDataLoadState = MutableLiveData<DataLoadState>(DataLoadState.Unloaded)

    val homeIndonesiaCoronaDataLoadState: LiveData<DataLoadState>
        get() = _homeIndonesiaCoronaDataLoadState
    private val _homeIndonesiaCoronaDataLoadState = MutableLiveData<DataLoadState>(DataLoadState.Unloaded)

    val homeGlobalCoronaDataLoadState: LiveData<DataLoadState>
        get() = _homeGlobalCoronaDataLoadState
    private val _homeGlobalCoronaDataLoadState = MutableLiveData<DataLoadState>(DataLoadState.Unloaded)

    fun getInformationIntroductionList(dispatcher: CoroutineDispatcher) {
        viewModelScope.launch(dispatcher) {
            fetchInformationIntroductionList()
        }
    }

    fun getInformationOtherList(dispatcher: CoroutineDispatcher) {
        viewModelScope.launch(dispatcher) {
            fetchInformationOtherList()
        }
    }

    fun getInformationLamanList(dispatcher: CoroutineDispatcher) {
        viewModelScope.launch(dispatcher) {
            fetchInformationLamanList()
        }
    }

    fun getHomeIndonesiaCoronaList(dispatcher: CoroutineDispatcher) {
        viewModelScope.launch(dispatcher) {
            fetchHomeIndonesiaCoronaData()
        }
    }

    fun getHomeGlobalCoronaList(dispatcher: CoroutineDispatcher) {
        viewModelScope.launch(dispatcher) {
            fetchHomeGlobalCoronaData()
        }
    }

    private fun fetchInformationIntroductionList() {
        _informationIntroductionDataLoadState.postValue(DataLoadState.Loading)
        val introductionDatabaseReference = covidRepository.getInformationIntroductionDatabaseReference()
        introductionDatabaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                _informationIntroductionDataLoadState.postValue(DataLoadState.Error(error.message))
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val children = dataSnapshot.children
                val dataList = mutableListOf<Content>()
                for (data in children) {
                    dataList.add(data.getValue(Content::class.java)!!)
                }
                _informationIntroductionDataLoadState.postValue(DataLoadState.Loaded(dataList))
            }
        })
    }

    private fun fetchInformationOtherList() {
        _informationOtherDataLoadState.postValue(DataLoadState.Loading)
        val otherDatabaseReference = covidRepository.getInformationOtherDatabaseReference()
        otherDatabaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                _informationOtherDataLoadState.postValue(DataLoadState.Error(error.message))
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val children = dataSnapshot.children
                val dataList = mutableListOf<Content>()
                for (data in children) {
                    dataList.add(data.getValue(Content::class.java)!!)
                }
                _informationOtherDataLoadState.postValue(DataLoadState.Loaded(dataList))
            }
        })
    }

    private fun fetchInformationLamanList() {
        _informationLamanDataLoadState.postValue(DataLoadState.Loading)
        val lamanDatabaseReference = covidRepository.getInformationLamanDatabaseReference()
        lamanDatabaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                _informationLamanDataLoadState.postValue(DataLoadState.Error(error.message))
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val children = dataSnapshot.children
                val dataList = mutableListOf<Content>()
                for (data in children) {
                    dataList.add(data.getValue(Content::class.java)!!)
                }
                _informationLamanDataLoadState.postValue(DataLoadState.Loaded(dataList))
            }
        })
    }

    private suspend fun fetchHomeIndonesiaCoronaData() {
        _homeIndonesiaCoronaDataLoadState.postValue(DataLoadState.Loading)
        try {
            val response = covidRepository.getCountryDataResponse("IDN")
            if (response.isSuccessful) {
                val data = response.body()!!
                _homeIndonesiaCoronaDataLoadState.postValue(DataLoadState.Loaded(data))
            } else {
                _homeIndonesiaCoronaDataLoadState.postValue(DataLoadState.Error("Gagal Memuat Data, Silahkan Periksa Koneksi Internet"))
            }
        } catch (exception: Exception) {
            _homeIndonesiaCoronaDataLoadState.postValue(DataLoadState.Error(exception.message ?: "Gagal Memuat Data, Silahkan Periksa Koneksi Internet"))
        }
    }

    private suspend fun fetchHomeGlobalCoronaData() {
        _homeGlobalCoronaDataLoadState.postValue(DataLoadState.Loading)
        try {
            val response = covidRepository.getGlobalDataResponse()
            if (response.isSuccessful) {
                val data = response.body()!!
                _homeGlobalCoronaDataLoadState.postValue(DataLoadState.Loaded(data))
            } else {
                _homeGlobalCoronaDataLoadState.postValue(DataLoadState.Error("Gagal Memuat Data, Silahkan Periksa Koneksi Internet"))
            }
        } catch (exception: Exception) {
            _homeGlobalCoronaDataLoadState.postValue(DataLoadState.Error(exception.message ?: "Gagal Memuat Data, Silahkan Periksa Koneksi Internet"))
        }
    }

    companion object {
        sealed class DataLoadState {
            class Loaded(val data: Any) : DataLoadState()
            object Unloaded : DataLoadState()
            object Loading : DataLoadState()
            class Error(val errorMessage: String) : DataLoadState()
        }
    }
}