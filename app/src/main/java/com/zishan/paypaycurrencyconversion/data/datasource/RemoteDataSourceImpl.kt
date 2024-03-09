package com.zishan.paypaycurrencyconversion.data.datasource

import com.zishan.paypaycurrencyconversion.data.repo.remoterepo.ExchangeCurrencyApi
import com.zishan.paypaycurrencyconversion.data.repo.remoterepo.models.CurrencyExchangeRatesResponse
import com.zishan.paypaycurrencyconversion.data.repo.remoterepo.models.CurrencyResponse
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