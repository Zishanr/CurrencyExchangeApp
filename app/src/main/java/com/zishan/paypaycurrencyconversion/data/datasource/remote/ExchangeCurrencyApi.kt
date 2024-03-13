package com.zishan.paypaycurrencyconversion.data.datasource.remote

import com.zishan.paypaycurrencyconversion.data.models.CurrencyExchangeRatesResponse
import retrofit2.Response
import retrofit2.http.GET

interface ExchangeCurrencyApi {

    @GET("currencies.json")
    suspend fun getCurrencies(): Response<Map<String, String>>

    @GET("latest.json")
    suspend fun getExchangeRates(): Response<CurrencyExchangeRatesResponse>

}