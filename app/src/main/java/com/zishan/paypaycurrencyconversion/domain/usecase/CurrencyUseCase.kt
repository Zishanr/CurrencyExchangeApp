package com.zishan.paypaycurrencyconversion.domain.usecase

import com.zishan.paypaycurrencyconversion.data.repository.PayPayRepo
import com.zishan.paypaycurrencyconversion.domain.datamapper.PayPayDataMapper
import com.zishan.paypaycurrencyconversion.view.uimodels.CurrencyTypeUIModel
import com.zishan.paypaycurrencyconversion.view.uimodels.ExchangeRateUIModel
import javax.inject.Inject

class CurrencyUseCase @Inject constructor(
    private val payPayRepo: PayPayRepo,
    private val dataMapper: PayPayDataMapper
) {
    private val currencyKeyList by lazy { mutableListOf<String>() }

    suspend fun getCurrenciesList(): List<CurrencyTypeUIModel> {
        val currencyData = payPayRepo.getCurrencies()
        currencyData.forEach {
            currencyKeyList.add(it.currencyName)
        }
        return dataMapper.mapToCurrencyUIList(currencyData)
    }

    suspend fun getExchangeRateData(
        selectedCurrencyIndex: Int,
        textValue: Double
    ): List<ExchangeRateUIModel> {
        if (selectedCurrencyIndex == 0) return listOf()
        val currencyData = payPayRepo.getExchangeRates()
        if (currencyData.isNotEmpty()) {
            val selectedCurrencyRate =
                payPayRepo.getSelectedCurrencyRate(currencyKeyList[selectedCurrencyIndex - 1])
            return dataMapper.mapToCurrencyUIModel(
                currencyData,
                selectedCurrencyRate,
                textValue
            )
        }
        return emptyList()
    }
}