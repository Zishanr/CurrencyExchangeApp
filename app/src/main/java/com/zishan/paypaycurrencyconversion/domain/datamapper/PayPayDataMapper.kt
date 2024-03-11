package com.zishan.paypaycurrencyconversion.domain.datamapper

import com.zishan.paypaycurrencyconversion.data.datasource.local.room.entities.CurrencyEntity
import com.zishan.paypaycurrencyconversion.view.uimodel.CurrencyTypeUIModel
import com.zishan.paypaycurrencyconversion.view.uimodel.ExchangeRateUIModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PayPayDataMapper @Inject constructor(private val dispatcher: Dispatchers) {

    suspend fun mapToCurrencyUIList(currencyData: List<CurrencyEntity>): List<CurrencyTypeUIModel> {
        return withContext(dispatcher.Default) {
            currencyData.map {
                CurrencyTypeUIModel("${it.currencyName} : ${it.currencyValue}")
            }.toMutableList().apply {
                this.add(0, CurrencyTypeUIModel("Select Currency"))
            }
        }
    }

    fun mapToCurrencyUIModel(
        rates: Map<String, Double>,
        currencyKeyList : List<String>,
        currencyIndex: Int,
        textValue: Double,
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