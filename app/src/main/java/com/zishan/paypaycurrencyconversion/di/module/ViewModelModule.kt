package com.zishan.paypaycurrencyconversion.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zishan.paypaycurrencyconversion.di.factory.ViewModelProviderFactory
import com.zishan.paypaycurrencyconversion.di.scope.ViewModelKey
import com.zishan.paypaycurrencyconversion.view.viewmodel.PayPayViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindsViewModelFactory(modelProvider: ViewModelProviderFactory) :ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(PayPayViewModel::class)
    abstract fun bindPayPayViewModel(payPayViewModel: PayPayViewModel) : ViewModel
}
