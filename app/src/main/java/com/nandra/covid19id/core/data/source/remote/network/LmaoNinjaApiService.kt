package com.nandra.covid19id.core.data.source.remote.network

import com.nandra.covid19id.core.data.source.remote.response.LmaoNinjaCountriesResponse
import com.nandra.covid19id.core.data.source.remote.response.LmaoNinjaCountryResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LmaoNinjaApiService {
    @GET("countries/{country}")
    fun getCountryData(
        @Path("country") country: String
    ) : Flowable<LmaoNinjaCountryResponse>

    @GET("all")
    fun getGlobalData(
        @Query("yesterday") includeYesterday: Boolean = false
    ) : Flowable<LmaoNinjaCountryResponse>

    @GET("countries")
    fun getCountriesData() : Flowable<LmaoNinjaCountriesResponse>
}