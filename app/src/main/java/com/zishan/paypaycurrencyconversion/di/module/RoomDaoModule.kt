package com.zishan.paypaycurrencyconversion.di.module

import com.zishan.paypaycurrencyconversion.data.datasource.local.room.dao.CurrencyDao
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.dao.ExchangeRateDao
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.dao.RefreshFrequencyDao
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.db.CurrencyDataBase
import com.zishan.paypaycurrencyconversion.di.scope.AppScope
import dagger.Module
import dagger.Provides

@Module
class RoomDaoModule {
    @Provides
    @AppScope
    fun provideCurrencyDao(currencyDataBase: CurrencyDataBase): CurrencyDao {
        return currencyDataBase.currencyDao
    }

    @Provides
    @AppScope
    fun provideRefreshFrequencyDao(currencyDataBase: CurrencyDataBase): RefreshFrequencyDao {
        return currencyDataBase.refreshFrequencyDao
    }

    @Provides
    @AppScope
    fun provideExchangeRateDao(currencyDataBase: CurrencyDataBase): ExchangeRateDao {
        return currencyDataBase.exchangeRateDao
    }
}