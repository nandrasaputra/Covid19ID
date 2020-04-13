package com.nandra.covid19id.network.response.model


import com.google.gson.annotations.SerializedName

data class Feature(
    @SerializedName("attributes")
    val attributes: Attributes
)