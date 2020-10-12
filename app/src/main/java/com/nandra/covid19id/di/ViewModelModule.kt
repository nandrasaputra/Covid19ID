package com.nandra.covid19id.di

import androidx.lifecycle.ViewModel
import com.nandra.covid19id.ui.home.HomeSharedViewModel
import com.nandra.covid19id.ui.information.InformationViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeSharedViewModel::class)
    abstract fun bindHomeSharedViewModel(viewModel: HomeSharedViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(InformationViewModel::class)
    abstract fun bindInformationViewModel(viewModel: InformationViewModel): ViewModel

}