package com.zishan.paypaycurrencyconversion.data.repository

import com.zishan.paypaycurrencyconversion.data.datasource.local.LocalDataSource
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.entities.CurrencyEntity
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.entities.ExchangeRateEntity
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.entities.RefreshFrequencyEntity
import com.zishan.paypaycurrencyconversion.data.datasource.remote.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PayPayRepo @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val dispatcher: Dispatchers

) {
    // TODO Fix code
    suspend fun getCurrencies(): List<CurrencyEntity> {
        return if (updateFromRemoteData("currency")) {
            val data = remoteDataSource.getCurrencies()
            withContext(dispatcher.IO) {
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

    // TODO Fix code
    suspend fun getExchangeRates(): List<ExchangeRateEntity> {
        return if (updateFromRemoteData("exchange_rate")) {
            val d = remoteDataSource.getExchangeRates()
            withContext(dispatcher.IO) {
                d.rates.forEach {
                    localDataSource.saveExchangeRates(
                        ExchangeRateEntity(
                            currencyName = it.key,
                            currencyValue = it.value
                        )
                    )
                }
                localDataSource.saveTimeStamp(
                    RefreshFrequencyEntity(
                        entityKey = "exchange_rate",
                        timeStamp = System.currentTimeMillis()
                    )
                )
            }
            localDataSource.getExchangeRates()
        } else {
            localDataSource.getExchangeRates()
        }
    }

    suspend fun getSelectedCurrencyRate(currencyKey: String): Double {
        return withContext(dispatcher.IO) {
            localDataSource.getSelectedCurrencyRate(currencyKey)
        }
    }


    //TODO Fix
    private suspend fun updateFromRemoteData(entityKey: String): Boolean {
        val diff = System.currentTimeMillis() - (localDataSource.getTimeStamp(entityKey) ?: 0L)
//        return ((diff / 1000) / 60) > 30
        return ((diff / 1000)) > 300
    }
}