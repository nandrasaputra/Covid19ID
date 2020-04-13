package com.nandra.covid19id.network.apiservice

import com.nandra.covid19id.network.interceptor.ConnectivityInterceptor
import com.nandra.covid19id.network.response.CountriesResponse
import com.nandra.covid19id.network.response.CountryResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LmaoNinjaApiService {

    @GET("countries/{country}")
    suspend fun getCountryData(
        @Path("country") country: String
    ) : Response<CountryResponse>

    @GET("countries")
    suspend fun getCountriesData() : Response<CountriesResponse>

    @GET("all")
    suspend fun getGlobalData(
        @Query("yesterday") includeYesterday: Boolean = false
    ) : Response<CountryResponse>

    companion object {
        operator fun invoke(connectivityInterceptor: ConnectivityInterceptor) : LmaoNinjaApiService {
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()
                return@Interceptor chain.proceed(request)
            }
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://corona.lmao.ninja/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(LmaoNinjaApiService::class.java)
        }
    }
}