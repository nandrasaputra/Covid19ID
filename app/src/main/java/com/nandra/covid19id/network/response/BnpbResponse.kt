package com.nandra.covid19id.network.response


import com.google.gson.annotations.SerializedName
import com.nandra.covid19id.network.response.model.Feature

data class BnpbResponse(
    @SerializedName("features")
    val features: List<Feature>
)