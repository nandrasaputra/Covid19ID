package com.nandra.covid19id.core.data.source.remote.network

import com.nandra.covid19id.core.data.source.remote.response.BnpbResponse
import io.reactivex.Flowable
import retrofit2.http.GET

interface BnpbApiService {
    @GET("VS6HdKS0VfIhv8Ct/arcgis/rest/services/COVID19_Indonesia_per_Provinsi/FeatureServer/0/query?where=1%3D1&outFields=*&outSR=4326&f=json")
    fun getIndonesiaProvinceCoronaData() : Flowable<BnpbResponse>
}