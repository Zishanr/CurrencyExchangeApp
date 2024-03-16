package com.zishan.paypaycurrencyconversion.data.repository

import com.zishan.paypaycurrencyconversion.common.CoroutineDispatchers
import com.zishan.paypaycurrencyconversion.data.datasource.local.LocalDataSource
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.entities.CurrencyEntity
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.entities.ExchangeRateEntity
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.entities.RefreshFrequencyEntity
import com.zishan.paypaycurrencyconversion.data.datasource.remote.RemoteDataSource
import com.zishan.paypaycurrencyconversion.data.models.CurrencyExchangeRatesResponse
import com.zishan.paypaycurrencyconversion.utils.PayPayConstant.DBConstant.CURRENCY_TIMESTAMP_KEY
import com.zishan.paypaycurrencyconversion.utils.PayPayConstant.DBConstant.EXCHANGE_RATE_TIMESTAMP_KEY
import com.zishan.paypaycurrencyconversion.utils.PayPayUtils
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PayPayRepoImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val dispatcher: CoroutineDispatchers,
    private val payPayUtils: PayPayUtils
) : PayPayRepo {
    override suspend fun getCurrencies(): List<CurrencyEntity> {
        return if (payPayUtils.isRefreshData(localDataSource.getTimeStamp(CURRENCY_TIMESTAMP_KEY))) {
            val currencyData = remoteDataSource.getCurrencies()
            withContext(dispatcher.io) {
                val currencyEntityList = currencyData.map {
                    CurrencyEntity(
                        currencyName = it.key,
                        currencyValue = it.value
                    )
                }
                currencyEntityList.forEach {
                    localDataSource.saveCurrencies(it)
                }
                localDataSource.saveTimeStamp(
                    RefreshFrequencyEntity(
                        entityKey = CURRENCY_TIMESTAMP_KEY,
                        timeStamp = System.currentTimeMillis()
                    )
                )
            }
            localDataSource.getCurrencies()
        } else {
            localDataSource.getCurrencies()
        }
    }

    override suspend fun getExchangeRates(): List<ExchangeRateEntity> {
        return if (payPayUtils.isRefreshData(
                localDataSource.getTimeStamp(
                    EXCHANGE_RATE_TIMESTAMP_KEY
                )
            )
        ) {
            val exchangeRateData: CurrencyExchangeRatesResponse
            try {
                exchangeRateData = remoteDataSource.getExchangeRates()
            } catch (t: Throwable) {
                val currencyDBData = localDataSource.getExchangeRates()
                if (currencyDBData.isEmpty()) {
                    throw t
                } else {
                    return currencyDBData
                }
            }

            withContext(dispatcher.io) {
                exchangeRateData.rates.forEach {
                    localDataSource.saveExchangeRates(
                        ExchangeRateEntity(
                            currencyName = it.key,
                            currencyValue = it.value
                        )
                    )
                }
                localDataSource.saveTimeStamp(
                    RefreshFrequencyEntity(
                        entityKey = EXCHANGE_RATE_TIMESTAMP_KEY,
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
        return withContext(dispatcher.io) {
            localDataSource.getSelectedCurrencyRate(currencyKey)
        }
    }
}