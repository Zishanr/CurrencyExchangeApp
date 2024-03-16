package com.zishan.paypaycurrencyconversion.data.repository

import com.zishan.paypaycurrencyconversion.data.datasource.local.room.entities.CurrencyEntity
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.entities.ExchangeRateEntity

interface PayPayRepo {
    suspend fun getCurrencies() : List<CurrencyEntity>
    suspend  fun getExchangeRates() : List<ExchangeRateEntity>
}