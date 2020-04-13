package com.nandra.covid19id.network.apiservice

import com.nandra.covid19id.network.interceptor.ConnectivityInterceptor
import com.nandra.covid19id.network.response.BnpbResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface BnpbApiService {

    @GET("VS6HdKS0VfIhv8Ct/arcgis/rest/services/COVID19_Indonesia_per_Provinsi/FeatureServer/0/query?where=1%3D1&outFields=*&outSR=4326&f=json")
    suspend fun getIndonesiaProvinceCoronaData() : Response<BnpbResponse>

    companion object {
        operator fun invoke(connectivityInterceptor: ConnectivityInterceptor) : BnpbApiService {
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
                .baseUrl("https://services5.arcgis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BnpbApiService::class.java)
        }
    }

}