package com.zishan.paypaycurrencyconversion.domain.datamapper

import com.zishan.paypaycurrencyconversion.common.CoroutineDispatchers
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.entities.CurrencyEntity
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.entities.ExchangeRateEntity
import com.zishan.paypaycurrencyconversion.utils.PayPayConstant.DEFAULT_CURRENCY_NAME
import com.zishan.paypaycurrencyconversion.utils.PayPayConstant.DEFAULT_CURRENCY_VALUE
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PayPayDataMapper @Inject constructor(private val dispatcher: CoroutineDispatchers) {

    suspend fun mapToCurrencyList(currencyData: List<CurrencyEntity>): List<CurrencyEntity> {
        // Using Default dispatcher as the parsing of list can be extensive
        return withContext(dispatcher.default) {
            currencyData.map {
                CurrencyEntity(
                    currencyName = it.currencyName,
                    currencyValue = "${it.currencyName} : ${it.currencyValue}"
                )
            }.toMutableList().apply {
                this.add(
                    0,
                    createDefaultCurrencyOption()
                )
            }
        }
    }

    suspend fun mapToCurrencyModel(
        exchangeRateList: List<ExchangeRateEntity>,
        selectedCurrencyRate: Double,
        textValue: Double,
    ): List<ExchangeRateEntity> {
        return withContext(dispatcher.default) {
            exchangeRateList.map {
                ExchangeRateEntity(
                    currencyName = it.currencyName,
                    currencyValue = (textValue * (it.currencyValue / selectedCurrencyRate))
                )
            }
        }
    }

    private fun createDefaultCurrencyOption(): CurrencyEntity {
       return CurrencyEntity(
            currencyName = DEFAULT_CURRENCY_NAME,
            currencyValue = DEFAULT_CURRENCY_VALUE
        )
    }
}