package com.zishan.paypaycurrencyconversion.domain.usecase

import com.zishan.paypaycurrencyconversion.data.repository.PayPayRepo
import com.zishan.paypaycurrencyconversion.domain.datamapper.PayPayDataMapper
import com.zishan.paypaycurrencyconversion.view.uimodel.CurrencyTypeUIModel
import com.zishan.paypaycurrencyconversion.view.uimodel.ExchangeRateUIModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CurrencyUseCase @Inject constructor(
    private val payPayRepo: PayPayRepo,
    private val dispatcher: Dispatchers,
    private val dataMapper: PayPayDataMapper
) {
    private val currencyKeyList by lazy { mutableListOf<String>() }

    suspend fun getCurrenciesList(): List<CurrencyTypeUIModel> {
        return withContext(dispatcher.IO) {
            val currencyData = payPayRepo.getCurrencies()
            currencyData.forEach {
                ensureActive()
                currencyKeyList.add(it.currencyName)
            }
            dataMapper.mapToCurrencyUIList(currencyData)
        }
    }

    // TODO fix nullable
    suspend fun getExchangeRateData(
        currencyIndex: Int,
        textValue: Double
    ): List<ExchangeRateUIModel>? {
        return withContext(dispatcher.IO) {
            val currencyData = payPayRepo.getExchangeRates()
            dataMapper.mapToCurrencyUIModel(
                currencyData.rates,
                currencyKeyList,
                currencyIndex,
                textValue
            )
        }
    }
}