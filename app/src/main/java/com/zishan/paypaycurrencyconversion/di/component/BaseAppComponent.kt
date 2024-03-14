package com.zishan.paypaycurrencyconversion.di.component

import android.content.Context
import com.zishan.paypaycurrencyconversion.common.CoroutineDispatchers
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

    fun getCoroutineDispatchers(): CoroutineDispatchers

}