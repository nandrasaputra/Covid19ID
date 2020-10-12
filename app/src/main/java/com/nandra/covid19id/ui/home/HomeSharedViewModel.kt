package com.nandra.covid19id.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.nandra.covid19id.core.data.Resource
import com.nandra.covid19id.core.domain.model.CountryCase
import com.nandra.covid19id.core.domain.model.GeneralCase
import com.nandra.covid19id.core.domain.model.IndonesiaProvinceCase
import com.nandra.covid19id.core.domain.usecase.CovidUseCase
import javax.inject.Inject

class HomeSharedViewModel @Inject constructor(
    private val covidUseCase: CovidUseCase
): ViewModel() {

    private var retryFunctionList: MutableList<() -> Unit> = mutableListOf()

    private var currentGlobalCaseSource: LiveData<Resource<GeneralCase>> =
        LiveDataReactiveStreams.fromPublisher(covidUseCase.getGlobalCase())
    private var currentIndonesiaCaseSource: LiveData<Resource<GeneralCase>> =
        LiveDataReactiveStreams.fromPublisher(covidUseCase.getIndonesiaCase())
    private var currentIndonesiaProvinceCaseSource: LiveData<Resource<List<IndonesiaProvinceCase>>> =
        LiveDataReactiveStreams.fromPublisher(covidUseCase.getIndonesiaProvinceCase())
    private var currentOtherCountriesCaseSource: LiveData<Resource<List<CountryCase>>> =
        LiveDataReactiveStreams.fromPublisher(covidUseCase.getOtherCountriesCase())

    val globalCaseData = MediatorLiveData<Resource<GeneralCase>>()
    val indonesiaCaseData = MediatorLiveData<Resource<GeneralCase>>()
    val indonesiaProvinceCaseData = MediatorLiveData<Resource<List<IndonesiaProvinceCase>>>()
    val otherCountriesCaseData = MediatorLiveData<Resource<List<CountryCase>>>()

    init {
        globalCaseData.addSource(currentGlobalCaseSource) {
            globalCaseData.value = it
            if (it is Resource.Error) {
                retryFunctionList.add(::updateGlobalCaseData)
            }
        }
        indonesiaCaseData.addSource(currentIndonesiaCaseSource) {
            indonesiaCaseData.value = it
            if (it is Resource.Error) {
                retryFunctionList.add(::updateIndonesiaCaseData)
            }
        }
        indonesiaProvinceCaseData.addSource(currentIndonesiaProvinceCaseSource) {
            indonesiaProvinceCaseData.value = it
            if (it is Resource.Error) {
                retryFunctionList.add(::updateIndonesiaProvinceCaseData)
            }
        }
        otherCountriesCaseData.addSource(currentOtherCountriesCaseSource) {
            otherCountriesCaseData.value = it
            if (it is Resource.Error) {
                retryFunctionList.add(::updateOtherCountriesCaseData)
            }
        }
    }

    fun retryAllFailed() {
        val currentList = retryFunctionList.toList()
        retryFunctionList.clear()
        currentList.forEach {
            it.invoke()
        }
    }

    private fun updateGlobalCaseData() {
        if (globalCaseData.value !is Resource.Loading) {
            globalCaseData.removeSource(currentGlobalCaseSource)
            currentGlobalCaseSource = LiveDataReactiveStreams.fromPublisher(covidUseCase.getGlobalCase())
            globalCaseData.addSource(currentGlobalCaseSource) {
                globalCaseData.value = it
                if (it is Resource.Error) {
                    retryFunctionList.add(::updateGlobalCaseData)
                }
            }
        }
    }

    private fun updateIndonesiaCaseData() {
        if (indonesiaCaseData.value !is Resource.Loading) {
            indonesiaCaseData.removeSource(currentIndonesiaCaseSource)
            currentIndonesiaCaseSource = LiveDataReactiveStreams.fromPublisher(covidUseCase.getIndonesiaCase())
            indonesiaCaseData.addSource(currentIndonesiaCaseSource) {
                indonesiaCaseData.value = it
                if (it is Resource.Error) {
                    retryFunctionList.add(::updateIndonesiaCaseData)
                }
            }
        }
    }

    private fun updateIndonesiaProvinceCaseData() {
        if (indonesiaProvinceCaseData.value !is Resource.Loading) {
            indonesiaProvinceCaseData.removeSource(currentIndonesiaProvinceCaseSource)
            currentIndonesiaProvinceCaseSource = LiveDataReactiveStreams.fromPublisher(covidUseCase.getIndonesiaProvinceCase())
            indonesiaProvinceCaseData.addSource(currentIndonesiaProvinceCaseSource) {
                indonesiaProvinceCaseData.value = it
                if (it is Resource.Error) {
                    retryFunctionList.add(::updateIndonesiaProvinceCaseData)
                }
            }
        }
    }

    private fun updateOtherCountriesCaseData() {
        if (otherCountriesCaseData.value !is Resource.Loading) {
            otherCountriesCaseData.removeSource(currentOtherCountriesCaseSource)
            currentOtherCountriesCaseSource = LiveDataReactiveStreams.fromPublisher(covidUseCase.getOtherCountriesCase())
            otherCountriesCaseData.addSource(currentOtherCountriesCaseSource) {
                otherCountriesCaseData.value = it
                if (it is Resource.Error) {
                    retryFunctionList.add(::updateOtherCountriesCaseData)
                }
            }
        }
    }
}