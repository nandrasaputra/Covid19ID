package com.nandra.covid19id.ui.information

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.nandra.covid19id.core.data.Resource
import com.nandra.covid19id.core.domain.model.InformationContent
import com.nandra.covid19id.core.domain.usecase.CovidUseCase
import javax.inject.Inject

class InformationViewModel @Inject constructor(
    private val covidUseCase: CovidUseCase
) : ViewModel() {

    private var retryFunctionList: MutableList<() -> Unit> = mutableListOf()

    private var currentInformationIntroductionSource: LiveData<Resource<List<InformationContent>>> =
        LiveDataReactiveStreams.fromPublisher(covidUseCase.getInformationIntroduction())
    private var currentInformationOtherSource: LiveData<Resource<List<InformationContent>>> =
        LiveDataReactiveStreams.fromPublisher(covidUseCase.getInformationOther())
    private var currentInformationWebPagesSource: LiveData<Resource<List<InformationContent>>> =
        LiveDataReactiveStreams.fromPublisher(covidUseCase.getInformationWebPages())

    val informationIntroductionData = MediatorLiveData<Resource<List<InformationContent>>>()
    val informationOtherData = MediatorLiveData<Resource<List<InformationContent>>>()
    val informationWebPagesData = MediatorLiveData<Resource<List<InformationContent>>>()

    init {
        informationIntroductionData.addSource(currentInformationIntroductionSource) {
            informationIntroductionData.value = it
            if (it is Resource.Error) {
                retryFunctionList.add(::updateInformationIntroductionData)
            }
        }
        informationOtherData.addSource(currentInformationOtherSource) {
            informationOtherData.value = it
            if (it is Resource.Error) {
                retryFunctionList.add(::updateInformationOtherData)
            }
        }
        informationWebPagesData.addSource(currentInformationWebPagesSource) {
            informationWebPagesData.value = it
            if (it is Resource.Error) {
                retryFunctionList.add(::updateInformationWebPagesData)
            }
        }
    }

    private fun updateInformationIntroductionData() {
        if (informationIntroductionData.value !is Resource.Loading) {
            informationIntroductionData.removeSource(currentInformationIntroductionSource)
            currentInformationIntroductionSource = LiveDataReactiveStreams.fromPublisher(covidUseCase.getInformationIntroduction())
            informationIntroductionData.addSource(currentInformationIntroductionSource) {
                informationIntroductionData.value = it
                if (it is Resource.Error) {
                    retryFunctionList.add(::updateInformationIntroductionData)
                }
            }
        }
    }

    private fun updateInformationOtherData() {
        if (informationOtherData.value !is Resource.Loading) {
            informationOtherData.removeSource(currentInformationOtherSource)
            currentInformationOtherSource = LiveDataReactiveStreams.fromPublisher(covidUseCase.getInformationOther())
            informationOtherData.addSource(currentInformationOtherSource) {
                informationOtherData.value = it
                if (it is Resource.Error) {
                    retryFunctionList.add(::updateInformationOtherData)
                }
            }
        }
    }

    private fun updateInformationWebPagesData() {
        if (informationWebPagesData.value !is Resource.Loading) {
            informationWebPagesData.removeSource(currentInformationWebPagesSource)
            currentInformationWebPagesSource = LiveDataReactiveStreams.fromPublisher(covidUseCase.getInformationWebPages())
            informationWebPagesData.addSource(currentInformationWebPagesSource) {
                informationWebPagesData.value = it
                if (it is Resource.Error) {
                    retryFunctionList.add(::updateInformationWebPagesData)
                }
            }
        }
    }
}