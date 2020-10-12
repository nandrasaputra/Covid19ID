package com.nandra.covid19id.core.di

import com.nandra.covid19id.core.data.CovidRepository
import com.nandra.covid19id.core.domain.repository.ICovidRepository
import dagger.Binds
import dagger.Module

@Suppress("unused")
@Module(includes = [NetworkModule::class])
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(covidRepository: CovidRepository): ICovidRepository

}