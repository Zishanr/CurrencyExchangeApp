package com.zishan.paypaycurrencyconversion.data.datasource.local.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.dao.CurrencyDao
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.dao.ExchangeRateDao
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.dao.RefreshFrequencyDao
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.entities.CurrencyEntity
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.entities.ExchangeRateEntity
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.entities.RefreshFrequencyEntity

@Database(
    entities = [CurrencyEntity::class, ExchangeRateEntity::class, RefreshFrequencyEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CurrencyDataBase : RoomDatabase() {
    abstract val currencyDao: CurrencyDao
    abstract val exchangeRateDao: ExchangeRateDao
    abstract val refreshFrequencyDao: RefreshFrequencyDao

}

