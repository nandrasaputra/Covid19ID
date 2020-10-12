package com.nandra.covid19id.core.domain.model

data class CountryCase(
    val id: Int,
    val title: String,
    val positiveCase: Int,
    val curedCase: Int,
    val deathCase: Int,
    val countryFlagImageUrl: String
)