package com.zishan.paypaycurrencyconversion.data.datasource.remote

import com.zishan.paypaycurrencyconversion.data.models.CurrencyExchangeRatesResponse
import com.zishan.paypaycurrencyconversion.utils.PayPayConstant.NetworkConst.ERROR_MESSAGE
import com.zishan.paypaycurrencyconversion.utils.PayPayConstant.NetworkConst.HTTP_SUCCESS
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val exchangeCurrencyApi: ExchangeCurrencyApi) :
    RemoteDataSource {
    override suspend fun getCurrencies(): Map<String, String> {
        return processResponse(exchangeCurrencyApi.getCurrencies())
    }

    override suspend fun getExchangeRates(): CurrencyExchangeRatesResponse {
        return processResponse(exchangeCurrencyApi.getExchangeRates())
    }

    private fun <T> processResponse(apiResponse: Response<T>): T {
        if (apiResponse.code() == HTTP_SUCCESS) {
            return apiResponse.body() ?: throw Throwable(ERROR_MESSAGE)
        } else {
            throw Throwable(apiResponse.errorBody().toString())
        }
    }
}