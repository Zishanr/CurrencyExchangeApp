package com.zishan.paypaycurrencyconversion.data.datasource.remote

import com.zishan.paypaycurrencyconversion.data.models.CurrencyExchangeRatesResponse
import com.zishan.paypaycurrencyconversion.data.models.CurrencyResponse

interface RemoteDataSource {
    suspend fun getCurrencies(): Map<String, String>
    suspend fun getExchangeRates(): CurrencyExchangeRatesResponse
}