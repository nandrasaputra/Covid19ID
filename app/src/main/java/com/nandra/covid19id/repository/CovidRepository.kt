package com.nandra.covid19id.repository

import android.app.Application
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.nandra.covid19id.network.apiservice.LmaoNinjaApiService
import com.nandra.covid19id.network.interceptor.ConnectivityInterceptor
import com.nandra.covid19id.network.response.CountriesResponse
import com.nandra.covid19id.network.response.CountryResponse
import retrofit2.Response

class CovidRepository(app: Application) {

    private val firebaseDatabaseReference = FirebaseDatabase.getInstance().reference
    private val interceptor = ConnectivityInterceptor(app)
    private val lmaoNinjaApiService = LmaoNinjaApiService(interceptor)

    fun getInformationIntroductionDatabaseReference() : DatabaseReference {
        return firebaseDatabaseReference.child("content/covid_introduction")
    }

    fun getInformationOtherDatabaseReference() : DatabaseReference {
        return firebaseDatabaseReference.child("content/covid_other")
    }

    fun getInformationLamanDatabaseReference() : DatabaseReference {
        return firebaseDatabaseReference.child("content/covid_laman")
    }

    suspend fun getCountryDataResponse(iso3CountryName: String) : Response<CountryResponse> {
        return lmaoNinjaApiService.getCountryData(iso3CountryName)
    }

    suspend fun getCountriesDataResponse() : Response<CountriesResponse> {
        return lmaoNinjaApiService.getCountriesData()
    }

    suspend fun getGlobalDataResponse() : Response<CountryResponse> {
        return lmaoNinjaApiService.getGlobalData()
    }
}