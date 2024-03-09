package com.zishan.paypaycurrencyconversion.data.repo.remoterepo


import com.zishan.paypaycurrencyconversion.data.repo.remoterepo.models.CurrencyExchangeRatesResponse
import com.zishan.paypaycurrencyconversion.data.repo.remoterepo.models.CurrencyResponse
import javax.inject.Inject

class CurrencyRemoteRepo @Inject constructor(private val exchangeCurrencyApi: ExchangeCurrencyApi) {

//    suspend fun getCurrencies(): CurrencyResponse {
//        return exchangeCurrencyApi.getCurrencies()
//    }

    suspend fun getExchangeRates(baseCurrency: String): CurrencyExchangeRatesResponse {
        // commenting as i can't use premium plan and free only provides for USD base
//        val result = apiService.getExchangeRates(baseCurrency)

        return exchangeCurrencyApi.getExchangeRates()
    }


//
//    private fun parseCurrencies(currencyMap: Map<String, String>): List<CurrencyModel> {
//        return currencyMap.map {
//            CurrencyModel(code = it.key, name = it.value)
//        }
//    }
//
//    private fun parseExchangeRates(currencyMap: Map<String, Double>): List<ExchangeRateModel> {
//        return currencyMap.map {
//            ExchangeRateModel(code = it.key, rate = it.value)
//        }
//    }
}