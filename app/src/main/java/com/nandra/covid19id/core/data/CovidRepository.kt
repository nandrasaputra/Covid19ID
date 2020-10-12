package com.nandra.covid19id.core.data

import com.nandra.covid19id.core.data.source.remote.RemoteDataSource
import com.nandra.covid19id.core.data.source.remote.network.ApiResponse
import com.nandra.covid19id.core.data.source.remote.response.BnpbResponse
import com.nandra.covid19id.core.data.source.remote.response.FirebaseInformationResponse
import com.nandra.covid19id.core.data.source.remote.response.LmaoNinjaCountriesResponse
import com.nandra.covid19id.core.data.source.remote.response.LmaoNinjaCountryResponse
import com.nandra.covid19id.core.domain.model.CountryCase
import com.nandra.covid19id.core.domain.model.GeneralCase
import com.nandra.covid19id.core.domain.model.IndonesiaProvinceCase
import com.nandra.covid19id.core.domain.model.InformationContent
import com.nandra.covid19id.core.domain.repository.ICovidRepository
import com.nandra.covid19id.utils.mapBnpbResponseToListIndonesiaProvinceCase
import com.nandra.covid19id.utils.mapListFirebaseInformationResponseToListInformationContent
import com.nandra.covid19id.utils.mapLmaoNinjaCountriesResponseToListCountryCase
import com.nandra.covid19id.utils.mapLmaoNinjaCountryResponseToGeneralCase
import io.reactivex.Flowable
import javax.inject.Inject

class CovidRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : ICovidRepository{

    override fun getGlobalCase(): Flowable<Resource<GeneralCase>> =
        object : RepositoryRxResourceWrapper<GeneralCase, LmaoNinjaCountryResponse>() {
            override fun createCall(): Flowable<ApiResponse<LmaoNinjaCountryResponse>> {
                return remoteDataSource.getGlobalCase()
            }

            override fun dataMapper(input: LmaoNinjaCountryResponse): GeneralCase {
                return mapLmaoNinjaCountryResponseToGeneralCase(input, "Global")
            }

            override fun emptyDataMapper(): GeneralCase {
                throw Exception("No Empty State")
            }
        }.asFlowable()

    override fun getIndonesiaCase(): Flowable<Resource<GeneralCase>> =
        object : RepositoryRxResourceWrapper<GeneralCase, LmaoNinjaCountryResponse>() {
            override fun createCall(): Flowable<ApiResponse<LmaoNinjaCountryResponse>> {
                return remoteDataSource.getIndonesiaCase()
            }

            override fun dataMapper(input: LmaoNinjaCountryResponse): GeneralCase {
                return mapLmaoNinjaCountryResponseToGeneralCase(input, "Indonesia")
            }

            override fun emptyDataMapper(): GeneralCase {
                throw Exception("No Empty State")
            }
        }.asFlowable()

    override fun getIndonesiaProvinceCase(): Flowable<Resource<List<IndonesiaProvinceCase>>> =
        object : RepositoryRxResourceWrapper<List<IndonesiaProvinceCase>, BnpbResponse>() {
            override fun createCall(): Flowable<ApiResponse<BnpbResponse>> {
                return remoteDataSource.getIndonesiaProvinceCase()
            }

            override fun dataMapper(input: BnpbResponse): List<IndonesiaProvinceCase> {
                return mapBnpbResponseToListIndonesiaProvinceCase(input)
            }

            override fun emptyDataMapper(): List<IndonesiaProvinceCase> {
                return listOf()
            }
        }.asFlowable()

    override fun getOtherCountriesCase(): Flowable<Resource<List<CountryCase>>> =
        object : RepositoryRxResourceWrapper<List<CountryCase>, LmaoNinjaCountriesResponse>() {
            override fun createCall(): Flowable<ApiResponse<LmaoNinjaCountriesResponse>> {
                return remoteDataSource.getOtherCountriesCase()
            }

            override fun dataMapper(input: LmaoNinjaCountriesResponse): List<CountryCase> {
                return mapLmaoNinjaCountriesResponseToListCountryCase(input)
            }

            override fun emptyDataMapper(): List<CountryCase> {
                return listOf()
            }
        }.asFlowable()

    override fun getInformationIntroduction(): Flowable<Resource<List<InformationContent>>> =
        object : RepositoryRxResourceWrapper<List<InformationContent>, List<FirebaseInformationResponse>>() {
            override fun createCall(): Flowable<ApiResponse<List<FirebaseInformationResponse>>> {
                return remoteDataSource.getInformationIntroduction()
            }

            override fun dataMapper(input: List<FirebaseInformationResponse>): List<InformationContent> {
                return mapListFirebaseInformationResponseToListInformationContent(input)
            }

            override fun emptyDataMapper(): List<InformationContent> {
                return listOf()
            }
        }.asFlowable()


    override fun getInformationOther(): Flowable<Resource<List<InformationContent>>> =
        object : RepositoryRxResourceWrapper<List<InformationContent>, List<FirebaseInformationResponse>>() {
            override fun createCall(): Flowable<ApiResponse<List<FirebaseInformationResponse>>> {
                return remoteDataSource.getInformationOther()
            }

            override fun dataMapper(input: List<FirebaseInformationResponse>): List<InformationContent> {
                return mapListFirebaseInformationResponseToListInformationContent(input)
            }

            override fun emptyDataMapper(): List<InformationContent> {
                return listOf()
            }
        }.asFlowable()

    override fun getInformationWebPages(): Flowable<Resource<List<InformationContent>>> =
        object : RepositoryRxResourceWrapper<List<InformationContent>, List<FirebaseInformationResponse>>() {
            override fun createCall(): Flowable<ApiResponse<List<FirebaseInformationResponse>>> {
                return remoteDataSource.getInformationWebPages()
            }

            override fun dataMapper(input: List<FirebaseInformationResponse>): List<InformationContent> {
                return mapListFirebaseInformationResponseToListInformationContent(input)
            }

            override fun emptyDataMapper(): List<InformationContent> {
                return listOf()
            }
        }.asFlowable()
}