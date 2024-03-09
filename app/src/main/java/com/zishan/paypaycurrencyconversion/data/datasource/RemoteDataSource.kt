package com.zishan.paypaycurrencyconversion.data.datasource

import com.zishan.paypaycurrencyconversion.data.repo.remoterepo.models.CurrencyExchangeRatesResponse
import com.zishan.paypaycurrencyconversion.data.repo.remoterepo.models.CurrencyResponse

interface RemoteDataSource {
    suspend fun getCurrencies(): Map<String, String>
    suspend fun getExchangeRates(): CurrencyExchangeRatesResponse
}