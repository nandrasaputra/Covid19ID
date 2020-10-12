package com.nandra.covid19id.core.domain.usecase

import com.nandra.covid19id.core.data.Resource
import com.nandra.covid19id.core.domain.model.CountryCase
import com.nandra.covid19id.core.domain.model.GeneralCase
import com.nandra.covid19id.core.domain.model.IndonesiaProvinceCase
import com.nandra.covid19id.core.domain.model.InformationContent
import io.reactivex.Flowable

interface CovidUseCase {
    fun getGlobalCase(): Flowable<Resource<GeneralCase>>
    fun getIndonesiaCase(): Flowable<Resource<GeneralCase>>
    fun getIndonesiaProvinceCase(): Flowable<Resource<List<IndonesiaProvinceCase>>>
    fun getOtherCountriesCase(): Flowable<Resource<List<CountryCase>>>
    fun getInformationIntroduction(): Flowable<Resource<List<InformationContent>>>
    fun getInformationOther(): Flowable<Resource<List<InformationContent>>>
    fun getInformationWebPages(): Flowable<Resource<List<InformationContent>>>
}