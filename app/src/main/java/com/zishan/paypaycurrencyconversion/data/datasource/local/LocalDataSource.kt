package com.zishan.paypaycurrencyconversion.data.datasource.local

import com.zishan.paypaycurrencyconversion.data.datasource.local.room.dao.CurrencyDao
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.dao.RefreshFrequencyDao
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.entities.CurrencyEntity
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.entities.RefreshFrequencyEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val currencyDao: CurrencyDao,
    private val refreshFrequencyDao: RefreshFrequencyDao
) {

    suspend fun saveCurrencies(currencyEntity: CurrencyEntity) {
        currencyDao.insertData(currencyEntity)
    }

    suspend fun getCurrencies(): List<CurrencyEntity> {
        return currencyDao.getAllData()
    }

    suspend fun saveExchangeRates() {

    }

    suspend fun getExchangeRates() {

    }

    suspend fun saveTimeStamp(refreshFrequencyEntity: RefreshFrequencyEntity) {
        refreshFrequencyDao.insertTimeStamp(refreshFrequencyEntity)
    }

    suspend fun getTimeStamp(entityKey: String): Long?{
         return refreshFrequencyDao.getTimeStamp(entityKey)
    }
}