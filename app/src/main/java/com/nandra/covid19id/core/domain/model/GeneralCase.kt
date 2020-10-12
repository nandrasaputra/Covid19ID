package com.nandra.covid19id.core.domain.model

class GeneralCase(
    val id: Int,
    val title: String,
    val positiveCase: Int,
    val curedCase: Int,
    val deathCase: Int,
    val lastUpdateDate: String
)