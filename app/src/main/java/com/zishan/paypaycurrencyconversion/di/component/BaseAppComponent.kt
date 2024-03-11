package com.zishan.paypaycurrencyconversion.di.component

import android.app.Application
import android.content.Context
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.dao.CurrencyDao
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.dao.RefreshFrequencyDao
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.db.CurrencyDataBase
import com.zishan.paypaycurrencyconversion.di.module.BaseAppModule
import com.zishan.paypaycurrencyconversion.di.qualifier.ApplicationContext
import com.zishan.paypaycurrencyconversion.di.scope.ApplicationScope
import dagger.Component
import retrofit2.Retrofit

@ApplicationScope
@Component(modules = [BaseAppModule::class])
interface BaseAppComponent {

    @ApplicationContext
    fun getApplicationContext(): Context

    fun getRetrofit(): Retrofit

    fun getAppDB(): CurrencyDataBase

}