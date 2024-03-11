package com.zishan.paypaycurrencyconversion.data.datasource.remote

import com.zishan.paypaycurrencyconversion.data.datasource.remote.ExchangeCurrencyApi
import com.zishan.paypaycurrencyconversion.data.datasource.remote.RemoteDataSource
import com.zishan.paypaycurrencyconversion.data.models.CurrencyExchangeRatesResponse
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val exchangeCurrencyApi: ExchangeCurrencyApi) :
    RemoteDataSource {

    override suspend fun getCurrencies(): Map<String, String> {
        return exchangeCurrencyApi.getCurrencies()
    }

    override suspend fun getExchangeRates(): CurrencyExchangeRatesResponse {
        return exchangeCurrencyApi.getExchangeRates()
    }
}