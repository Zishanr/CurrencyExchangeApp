package com.zishan.paypaycurrencyconversion

import android.app.Application
import com.zishan.paypaycurrencyconversion.di.component.BaseAppComponent
import com.zishan.paypaycurrencyconversion.di.component.DaggerBaseAppComponent
import com.zishan.paypaycurrencyconversion.di.module.BaseAppModule

class MainApplication : Application() {
    fun getBaseComponent(): BaseAppComponent {
        return DaggerBaseAppComponent.builder().baseAppModule(BaseAppModule(this)).build()
    }
}