package com.nandra.covid19id.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

class LmaoNinjaCountriesResponse: ArrayList<LmaoNinjaCountryResponse>()

data class LmaoNinjaCountryResponse(
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

data class CountryInfo(
    @SerializedName("iso3")
    val iso3Name: String,
    @SerializedName("flag")
    val flagImagePath: String
)