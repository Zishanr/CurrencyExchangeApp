package com.zishan.paypaycurrencyconversion.usecase

import com.zishan.paypaycurrencyconversion.data.repository.PayPayRepo
import com.zishan.paypaycurrencyconversion.view.uimodel.ExchangeRateUIModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CurrencyUseCase @Inject constructor(
    private val payPayRepo: PayPayRepo,
    private val dispatcher: Dispatchers
) {
    private val currencyKeyList by lazy { mutableListOf<String>() }

    suspend fun getCurrenciesList(): List<String> {
        return withContext(dispatcher.IO) {
            val currencyData = payPayRepo.getCurrencies()
            currencyKeyList.addAll(currencyData.keys)
            convertToList(currencyData)
        }
    }

    private suspend fun convertToList(currencyData: Map<String, String>): List<String> {
        return withContext(dispatcher.Default) {
            currencyData.map {
                "${it.key} : ${it.value}"
            }.toMutableList().apply {
                this.add(0, "Select Currency")
            }
        }
    }

    suspend fun getExchangeRateData(currencyIndex: Int, textValue: Double): List<ExchangeRateUIModel>? {
        return withContext(dispatcher.IO) {
            val currencyData = payPayRepo.getExchangeRates()
            mapToCurrencyUIModel(currencyData.rates, currencyIndex, textValue)
        }
    }

    private fun mapToCurrencyUIModel(
        rates: Map<String, Double>,
        currencyIndex: Int,
        textValue: Double
    ): List<ExchangeRateUIModel>? {
        if (currencyIndex == 0) return listOf()
        val currencyKey = currencyKeyList[currencyIndex - 1]
        val currencyRate = rates[currencyKey]
        return currencyRate?.run {
            rates.map {
                ExchangeRateUIModel(it.key, (textValue * (it.value / this)))
            }
        }
    }
}