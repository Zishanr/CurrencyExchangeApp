package com.zishan.paypaycurrencyconversion.di.module

import android.content.Context
import androidx.room.Room
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.db.CurrencyDataBase
import com.zishan.paypaycurrencyconversion.di.qualifier.ApplicationContext
import com.zishan.paypaycurrencyconversion.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class BaseAppDBModule {

    // TODO
    @Provides
    @ApplicationScope
    fun provideCurrencyDB(@ApplicationContext applicationContext: Context): CurrencyDataBase {
        return Room.databaseBuilder(
            applicationContext,
            CurrencyDataBase::class.java,
            "currency-database"
        ).build()
    }
}