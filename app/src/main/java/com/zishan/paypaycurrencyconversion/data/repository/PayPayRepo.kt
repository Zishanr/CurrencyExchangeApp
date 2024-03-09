package com.zishan.paypaycurrencyconversion.data.repository

import com.zishan.paypaycurrencyconversion.data.datasource.LocalDataSource
import com.zishan.paypaycurrencyconversion.data.datasource.RemoteDataSource
import com.zishan.paypaycurrencyconversion.data.repo.remoterepo.models.CurrencyExchangeRatesResponse
import javax.inject.Inject

class PayPayRepo @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {
    suspend fun getCurrencies(): Map<String, String> {
        return remoteDataSource.getCurrencies()
    }

    suspend fun getExchangeRates() : CurrencyExchangeRatesResponse {
        return remoteDataSource.getExchangeRates()
    }
}