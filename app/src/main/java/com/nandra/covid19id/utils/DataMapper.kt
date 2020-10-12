package com.nandra.covid19id.utils

import com.nandra.covid19id.core.data.source.remote.response.BnpbResponse
import com.nandra.covid19id.core.data.source.remote.response.FirebaseInformationResponse
import com.nandra.covid19id.core.data.source.remote.response.LmaoNinjaCountriesResponse
import com.nandra.covid19id.core.data.source.remote.response.LmaoNinjaCountryResponse
import com.nandra.covid19id.core.domain.model.CountryCase
import com.nandra.covid19id.core.domain.model.GeneralCase
import com.nandra.covid19id.core.domain.model.IndonesiaProvinceCase
import com.nandra.covid19id.core.domain.model.InformationContent
import java.text.SimpleDateFormat
import java.util.*

fun mapLmaoNinjaCountryResponseToGeneralCase(input: LmaoNinjaCountryResponse, title: String): GeneralCase {
    return GeneralCase(
        input.id,
        title,
        input.totalCase,
        input.totalRecovered,
        input.totalDeath,
        convertMillisecondToStringDate(input.updateDate)
    )
}

fun mapBnpbResponseToListIndonesiaProvinceCase(input: BnpbResponse): List<IndonesiaProvinceCase> {
    return input.features.map {
        IndonesiaProvinceCase(
            it.attributes.fID,
            it.attributes.namaProvinsi,
            it.attributes.kasusPositif,
            it.attributes.kasusSembuh,
            it.attributes.kasusMeninggal
        )
    }
}

fun mapLmaoNinjaCountriesResponseToListCountryCase(input: LmaoNinjaCountriesResponse): List<CountryCase> {
    val dataList = input.toList()
    return dataList.map {
        CountryCase(
            it.id,
            it.countryName,
            it.totalCase,
            it.totalRecovered,
            it.totalDeath,
            it.countryInfo.flagImagePath
        )
    }
}

fun mapListFirebaseInformationResponseToListInformationContent(
    input: List<FirebaseInformationResponse>
) : List<InformationContent> {
    return input.map {
        InformationContent(
            it.id,
            it.title,
            it.description,
            it.image_path,
            it.website_path
        )
    }
}

private fun convertMillisecondToStringDate(millisecond: Long) : String {
    val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm")
    return formatter.format(Date(millisecond))
}