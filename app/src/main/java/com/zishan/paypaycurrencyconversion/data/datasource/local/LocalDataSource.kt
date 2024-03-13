package com.zishan.paypaycurrencyconversion.data.datasource.local

import com.zishan.paypaycurrencyconversion.data.datasource.local.room.dao.CurrencyDao
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.dao.ExchangeRateDao
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.dao.RefreshFrequencyDao
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.entities.CurrencyEntity
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.entities.ExchangeRateEntity
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.entities.RefreshFrequencyEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val currencyDao: CurrencyDao,
    private val exchangeRateDao: ExchangeRateDao,
    private val refreshFrequencyDao: RefreshFrequencyDao
) {
    suspend fun saveCurrencies(currencyEntity: CurrencyEntity) {
        currencyDao.insertCurrenciesData(currencyEntity)
    }

    suspend fun getCurrencies(): List<CurrencyEntity> {
        return currencyDao.getCurrenciesData()
    }

    suspend fun saveExchangeRates(exchangeRateEntity: ExchangeRateEntity) {
        exchangeRateDao.insertExchangeRateData(exchangeRateEntity)
    }

    suspend fun getExchangeRates(): List<ExchangeRateEntity> {
        return exchangeRateDao.getExchangeRateData()
    }

    suspend fun getSelectedCurrencyRate(currencyKey : String): Double {
        return exchangeRateDao.getSelectedCurrencyRate(currencyKey)
    }

    suspend fun saveTimeStamp(refreshFrequencyEntity: RefreshFrequencyEntity) {
        refreshFrequencyDao.insertTimeStamp(refreshFrequencyEntity)
    }

    suspend fun getTimeStamp(entityKey: String): Long? {
        return refreshFrequencyDao.getTimeStamp(entityKey)
    }
}