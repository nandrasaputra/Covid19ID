package com.nandra.covid19id.network.response

import com.google.gson.annotations.SerializedName
import com.nandra.covid19id.network.response.model.CountryInfo

data class CountryResponse(
    @SerializedName("_id")
    val id: Int,
    @SerializedName("updated")
    val updateDate: Long,
    @SerializedName("country")
    val countryName: String,
    val countryInfo: CountryInfo,
    @SerializedName("cases")
    val totalCase: Int,
    @SerializedName("deaths")
    val totalDeath: Int,
    @SerializedName("recovered")
    val totalRecovered: Int
)