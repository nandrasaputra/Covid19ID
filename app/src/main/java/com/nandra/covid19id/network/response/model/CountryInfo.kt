package com.nandra.covid19id.network.response.model

import com.google.gson.annotations.SerializedName

data class CountryInfo(
    @SerializedName("iso3")
    val iso3Name: String,
    @SerializedName("flag")
    val flagImagePath: String
)