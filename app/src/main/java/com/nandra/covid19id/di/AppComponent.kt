package com.nandra.covid19id.di

import com.nandra.covid19id.ui.MainActivity
import com.nandra.covid19id.ui.home.HomeFragment
import com.nandra.covid19id.ui.home.indonesiaprovincedetail.IndonesiaProvinceDetailFragment
import com.nandra.covid19id.ui.home.othercountriesdetail.OtherCountriesDetailFragment
import com.nandra.covid19id.ui.information.InformationFragment
import dagger.Subcomponent

@AppScope
@Subcomponent(
    modules = [AppModule::class, ViewModelModule::class]
)
interface AppComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): AppComponent
    }

    fun inject(activity: MainActivity)
    fun inject(fragment: HomeFragment)
    fun inject(fragment: InformationFragment)
    fun inject(fragment: IndonesiaProvinceDetailFragment)
    fun inject(fragment: OtherCountriesDetailFragment)

}