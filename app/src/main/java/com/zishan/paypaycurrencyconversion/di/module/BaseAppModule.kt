package com.zishan.paypaycurrencyconversion.di.module

import android.content.Context
import com.zishan.paypaycurrencyconversion.di.qualifier.ApplicationContext
import com.zishan.paypaycurrencyconversion.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides

@Module(includes = [NetworkModule::class, BaseAppDBModule::class])
class BaseAppModule(private var context: Context ) {

    @Provides
    @ApplicationScope
    @ApplicationContext
    fun providesApplicationContext() : Context {
        return context.applicationContext
    }
}