package com.nandra.covid19id.core.domain.usecase

import com.nandra.covid19id.core.domain.repository.ICovidRepository
import javax.inject.Inject

class CovidInteractor @Inject constructor(
    private val covidRepository: ICovidRepository
) : CovidUseCase {
    override fun getGlobalCase() = covidRepository.getGlobalCase()
    override fun getIndonesiaCase() = covidRepository.getIndonesiaCase()
    override fun getIndonesiaProvinceCase() = covidRepository.getIndonesiaProvinceCase()
    override fun getOtherCountriesCase() = covidRepository.getOtherCountriesCase()
    override fun getInformationIntroduction() = covidRepository.getInformationIntroduction()
    override fun getInformationOther() = covidRepository.getInformationOther()
    override fun getInformationWebPages() = covidRepository.getInformationWebPages()
}