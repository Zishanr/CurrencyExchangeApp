package com.zishan.paypaycurrencyconversion.domain.usecase

import com.zishan.paypaycurrencyconversion.data.datasource.local.room.entities.CurrencyEntity
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.entities.ExchangeRateEntity
import com.zishan.paypaycurrencyconversion.data.repository.PayPayRepoImpl
import com.zishan.paypaycurrencyconversion.domain.datamapper.PayPayDataMapper
import javax.inject.Inject

class CurrencyUseCase @Inject constructor(
    private val payPayRepoImpl: PayPayRepoImpl,
    private val dataMapper: PayPayDataMapper
) {
    private val currencyKeyList by lazy { mutableListOf<String>() }

    suspend fun getCurrenciesList(): List<CurrencyEntity> {
        val currencyData = payPayRepoImpl.getCurrencies()
        saveCurrencyKeyList(currencyData)
        return dataMapper.mapToCurrencyList(currencyData)
    }

    fun saveCurrencyKeyList(currencyData: List<CurrencyEntity>) {
        currencyData.forEach {
            currencyKeyList.add(it.currencyName)
        }
    }

    suspend fun getExchangeRateData(
        selectedCurrencyIndex: Int,
        textValue: Double
    ): List<ExchangeRateEntity> {
        if (selectedCurrencyIndex == 0) return listOf()
        val currencyData = payPayRepoImpl.getExchangeRates()
        if (currencyData.isNotEmpty()) {
            val selectedCurrencyRate =
                payPayRepoImpl.getSelectedCurrencyRate(currencyKeyList[selectedCurrencyIndex - 1])
            return dataMapper.mapToCurrencyModel(
                currencyData,
                selectedCurrencyRate,
                textValue
            )
        }
        return emptyList()
    }
}