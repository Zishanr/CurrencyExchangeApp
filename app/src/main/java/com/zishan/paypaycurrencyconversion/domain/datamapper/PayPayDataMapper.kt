package com.zishan.paypaycurrencyconversion.domain.datamapper

import com.zishan.paypaycurrencyconversion.data.datasource.local.room.entities.CurrencyEntity
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.entities.ExchangeRateEntity
import com.zishan.paypaycurrencyconversion.view.uimodels.CurrencyTypeUIModel
import com.zishan.paypaycurrencyconversion.view.uimodels.ExchangeRateUIModel
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
        exchangeRateList: List<ExchangeRateEntity>,
        selectedCurrencyRate: Double,
        textValue: Double,
    ): List<ExchangeRateUIModel> {
        return exchangeRateList.map {
            ExchangeRateUIModel(
                it.currencyName,
                (textValue * (it.currencyValue / selectedCurrencyRate))
            )
        }
    }
}