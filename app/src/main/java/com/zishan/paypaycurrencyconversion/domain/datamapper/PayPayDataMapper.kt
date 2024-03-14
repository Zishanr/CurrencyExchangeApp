package com.zishan.paypaycurrencyconversion.domain.datamapper

import com.zishan.paypaycurrencyconversion.common.CoroutineDispatchers
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.entities.CurrencyEntity
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.entities.ExchangeRateEntity
import com.zishan.paypaycurrencyconversion.domain.uimodels.CurrencyTypeUIModel
import com.zishan.paypaycurrencyconversion.domain.uimodels.ExchangeRateUIModel
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PayPayDataMapper @Inject constructor(private val dispatcher: CoroutineDispatchers) {

    suspend fun mapToCurrencyUIList(currencyData: List<CurrencyEntity>): List<CurrencyTypeUIModel> {
        // Using Default dispatcher as the parsing of list can be extensive
        return withContext(dispatcher.default) {
            currencyData.map {
                CurrencyTypeUIModel("${it.currencyName} : ${it.currencyValue}")
            }.toMutableList().apply {
                this.add(0, CurrencyTypeUIModel("Select Currency"))
            }
        }
    }

    // TODO
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