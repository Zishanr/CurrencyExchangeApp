package com.zishan.paypaycurrencyconversion.data.repository

import com.zishan.paypaycurrencyconversion.data.datasource.local.LocalDataSource
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.entities.CurrencyEntity
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.entities.RefreshFrequencyEntity
import com.zishan.paypaycurrencyconversion.data.datasource.remote.RemoteDataSource
import com.zishan.paypaycurrencyconversion.data.models.CurrencyExchangeRatesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PayPayRepo @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {
    // TODO Fix
    suspend fun getCurrencies(): List<CurrencyEntity> {
        return if (needToRefresh("currency")) {
            val data = remoteDataSource.getCurrencies()
            withContext(Dispatchers.IO) {
                val d = data.map { CurrencyEntity(currencyName = it.key, currencyValue = it.value) }
                d.forEach {
                    localDataSource.saveCurrencies(it)
                }
                localDataSource.saveTimeStamp(
                    RefreshFrequencyEntity(
                        entityKey = "currency",
                        timeStamp = System.currentTimeMillis()
                    )
                )
            }
            localDataSource.getCurrencies()
        } else {
            localDataSource.getCurrencies()
        }

    }

    suspend fun getExchangeRates(): CurrencyExchangeRatesResponse {
        return remoteDataSource.getExchangeRates()
    }


    //TODO Fix
    private suspend fun needToRefresh(entityKey: String): Boolean {
        val diff = System.currentTimeMillis() - (localDataSource.getTimeStamp(entityKey) ?: 0L)
//        return ((diff / 1000) / 60) > 30
        return ((diff / 1000)) > 30
    }
}