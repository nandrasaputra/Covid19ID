package com.nandra.covid19id.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class BnpbResponse(
    @SerializedName("features")
    val features: List<Feature>
)

data class Feature(
    @SerializedName("attributes")
    val attributes: Attributes
)

data class Attributes(
    @SerializedName("FID")
    val fID: Int,
    @SerializedName("Kasus_Meni")
    val kasusMeninggal: Int,
    @SerializedName("Kasus_Posi")
    val kasusPositif: Int,
    @SerializedName("Kasus_Semb")
    val kasusSembuh: Int,
    @SerializedName("Kode_Provi")
    val kodeProvinsi: Int,
    @SerializedName("Provinsi")
    val namaProvinsi: String
)