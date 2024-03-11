package com.zishan.paypaycurrencyconversion.di.component

import com.zishan.paypaycurrencyconversion.di.module.PayPayAppModule
import com.zishan.paypaycurrencyconversion.di.module.RoomDaoModule
import com.zishan.paypaycurrencyconversion.di.module.ViewModelModule
import com.zishan.paypaycurrencyconversion.di.scope.AppScope
import com.zishan.paypaycurrencyconversion.view.fragments.PayPayFragment
import dagger.Component

@AppScope
@Component(
    modules = [PayPayAppModule::class, ViewModelModule::class, RoomDaoModule::class],
    dependencies = [BaseAppComponent::class]
)
interface PayPayComponent {
    fun inject(payPayFragment : PayPayFragment)
}