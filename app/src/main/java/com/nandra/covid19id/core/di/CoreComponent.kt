package com.nandra.covid19id.core.di

import android.content.Context
import com.nandra.covid19id.di.AppComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RepositoryModule::class])
interface CoreComponent {

    fun getAppComponentFactory(): AppComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): CoreComponent
    }

}