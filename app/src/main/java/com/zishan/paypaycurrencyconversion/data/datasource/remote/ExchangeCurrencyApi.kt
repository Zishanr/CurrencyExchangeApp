package com.zishan.paypaycurrencyconversion.data.datasource.remote

import com.zishan.paypaycurrencyconversion.data.models.CurrencyExchangeRatesResponse
import com.zishan.paypaycurrencyconversion.data.models.CurrencyResponse
import retrofit2.http.GET

interface ExchangeCurrencyApi {

    @GET("currencies.json?app_id=b8af16750bbe4727bc5a7d031423a5c3")
    suspend fun getCurrencies(): Map<String, String>

    @GET("latest.json?app_id=b8af16750bbe4727bc5a7d031423a5c3")
    suspend fun getExchangeRates(): CurrencyExchangeRatesResponse

}