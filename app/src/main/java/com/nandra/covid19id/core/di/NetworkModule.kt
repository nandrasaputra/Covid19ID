package com.nandra.covid19id.core.di

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.nandra.covid19id.core.data.source.remote.network.BnpbApiService
import com.nandra.covid19id.core.data.source.remote.network.LmaoNinjaApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun provideBnpbApiService(client: OkHttpClient): BnpbApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://services5.arcgis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
        return  retrofit.create(BnpbApiService::class.java)
    }

    @Provides
    fun provideLmaoNinjaApiService(client: OkHttpClient): LmaoNinjaApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://corona.lmao.ninja/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
        return  retrofit.create(LmaoNinjaApiService::class.java)
    }

    @Provides
    fun provideFirebaseDatabaseReference(): DatabaseReference {
        return FirebaseDatabase.getInstance().reference
    }
}