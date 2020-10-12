package com.nandra.covid19id.di

import com.nandra.covid19id.core.domain.usecase.CovidInteractor
import com.nandra.covid19id.core.domain.usecase.CovidUseCase
import dagger.Binds
import dagger.Module

@Suppress("unused")
@Module
abstract class AppModule {

    @Binds
    abstract fun provideCovidUseCase(covidInteractor: CovidInteractor): CovidUseCase

}