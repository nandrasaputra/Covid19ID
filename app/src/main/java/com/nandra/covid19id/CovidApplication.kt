package com.nandra.covid19id

import android.app.Application
import com.nandra.covid19id.core.di.CoreComponent
import com.nandra.covid19id.core.di.DaggerCoreComponent
import com.nandra.covid19id.di.AppComponent

class CovidApplication: Application() {

    private val coreComponent: CoreComponent by lazy {
        DaggerCoreComponent.factory().create(applicationContext)
    }

    val appComponent: AppComponent by lazy {
        coreComponent.getAppComponentFactory().create()
    }

}