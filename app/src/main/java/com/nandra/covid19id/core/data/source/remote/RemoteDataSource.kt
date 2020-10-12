package com.nandra.covid19id.core.data.source.remote

import android.annotation.SuppressLint
import com.google.firebase.database.DatabaseReference
import com.nandra.covid19id.core.data.source.remote.network.ApiResponse
import com.nandra.covid19id.core.data.source.remote.network.BnpbApiService
import com.nandra.covid19id.core.data.source.remote.network.LmaoNinjaApiService
import com.nandra.covid19id.core.data.source.remote.response.BnpbResponse
import com.nandra.covid19id.core.data.source.remote.response.FirebaseInformationResponse
import com.nandra.covid19id.core.data.source.remote.response.LmaoNinjaCountriesResponse
import com.nandra.covid19id.core.data.source.remote.response.LmaoNinjaCountryResponse
import com.nandra.covid19id.utils.observeSingleValueEvent
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val bnpbApiService: BnpbApiService,
    private val lmaoNinjaApiService: LmaoNinjaApiService,
    private val firebaseDatabaseReference: DatabaseReference
) {

    @SuppressLint("CheckResult")
    fun getIndonesiaProvinceCase(): Flowable<ApiResponse<BnpbResponse>> {
        val result = PublishSubject.create<ApiResponse<BnpbResponse>>()
        bnpbApiService.getIndonesiaProvinceCoronaData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe(
                { response ->
                    result.onNext(
                        if (response.features.isNotEmpty())
                            ApiResponse.Success(response)
                        else
                            ApiResponse.Empty
                    )
                },
                { error ->
                    result.onNext(ApiResponse.Error(error.message.toString()))
                }
            )

        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    @SuppressLint("CheckResult")
    fun getOtherCountriesCase(): Flowable<ApiResponse<LmaoNinjaCountriesResponse>> {
        val result = PublishSubject.create<ApiResponse<LmaoNinjaCountriesResponse>>()
        lmaoNinjaApiService.getCountriesData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe(
                { response ->
                    result.onNext(
                        if (response.isNotEmpty())
                            ApiResponse.Success(response)
                        else
                            ApiResponse.Empty
                    )
                },
                { error ->
                    result.onNext(ApiResponse.Error(error.message.toString()))
                }
            )

        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    @SuppressLint("CheckResult")
    fun getIndonesiaCase(): Flowable<ApiResponse<LmaoNinjaCountryResponse>> {
        val result = PublishSubject.create<ApiResponse<LmaoNinjaCountryResponse>>()
        lmaoNinjaApiService.getCountryData("IDN")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe(
                { response ->
                    result.onNext(ApiResponse.Success(response))
                },
                { error ->
                    result.onNext(ApiResponse.Error(error.message.toString()))
                }
            )

        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    @SuppressLint("CheckResult")
    fun getGlobalCase(): Flowable<ApiResponse<LmaoNinjaCountryResponse>> {
        val result = PublishSubject.create<ApiResponse<LmaoNinjaCountryResponse>>()
        lmaoNinjaApiService.getGlobalData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe(
                { response ->
                    result.onNext(ApiResponse.Success(response))
                },
                { error ->
                    result.onNext(ApiResponse.Error(error.message.toString()))
                }
            )

        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    @SuppressLint("CheckResult")
    fun getInformationIntroduction(): Flowable<ApiResponse<List<FirebaseInformationResponse>>> {
        val result = PublishSubject.create<ApiResponse<List<FirebaseInformationResponse>>>()
        observeSingleValueEvent(firebaseDatabaseReference.child("content/covid_introduction"))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe (
                { dataSnapshot ->
                    val children = dataSnapshot.children
                    val dataList = mutableListOf<FirebaseInformationResponse>()
                    for (data in children) {
                        dataList.add(data.getValue(FirebaseInformationResponse::class.java)!!)
                    }
                    result.onNext(ApiResponse.Success(dataList))
                },
                { error ->
                    result.onNext(ApiResponse.Error(error.message.toString()))
                }
            )
        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    @SuppressLint("CheckResult")
    fun getInformationOther(): Flowable<ApiResponse<List<FirebaseInformationResponse>>> {
        val result = PublishSubject.create<ApiResponse<List<FirebaseInformationResponse>>>()
        observeSingleValueEvent(firebaseDatabaseReference.child("content/covid_other"))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe (
                { dataSnapshot ->
                    val children = dataSnapshot.children
                    val dataList = mutableListOf<FirebaseInformationResponse>()
                    for (data in children) {
                        dataList.add(data.getValue(FirebaseInformationResponse::class.java)!!)
                    }
                    result.onNext(ApiResponse.Success(dataList))
                },
                { error ->
                    result.onNext(ApiResponse.Error(error.message.toString()))
                }
            )
        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    @SuppressLint("CheckResult")
    fun getInformationWebPages(): Flowable<ApiResponse<List<FirebaseInformationResponse>>> {
        val result = PublishSubject.create<ApiResponse<List<FirebaseInformationResponse>>>()
        observeSingleValueEvent(firebaseDatabaseReference.child("content/covid_laman"))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe (
                { dataSnapshot ->
                    val children = dataSnapshot.children
                    val dataList = mutableListOf<FirebaseInformationResponse>()
                    for (data in children) {
                        dataList.add(data.getValue(FirebaseInformationResponse::class.java)!!)
                    }
                    result.onNext(ApiResponse.Success(dataList))
                },
                { error ->
                    result.onNext(ApiResponse.Error(error.message.toString()))
                }
            )
        return result.toFlowable(BackpressureStrategy.BUFFER)
    }
}