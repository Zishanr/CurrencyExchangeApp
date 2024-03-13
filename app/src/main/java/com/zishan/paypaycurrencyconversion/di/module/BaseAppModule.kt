package com.zishan.paypaycurrencyconversion.di.module

import android.app.Application
import android.content.Context
import com.zishan.paypaycurrencyconversion.di.qualifier.ApplicationContext
import com.zishan.paypaycurrencyconversion.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides

@Module(includes = [NetworkModule::class, BaseAppDBModule::class])
class BaseAppModule(private var application : Application) {

    @Provides
    @ApplicationScope
    fun providesApplication() : Application {
        return application
    }

    @Provides
    @ApplicationScope
    @ApplicationContext
    fun providesApplicationContext() : Context {
        return application.applicationContext
    }
}