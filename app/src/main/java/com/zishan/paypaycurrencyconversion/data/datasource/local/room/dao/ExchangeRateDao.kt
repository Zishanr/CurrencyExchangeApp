package com.zishan.paypaycurrencyconversion.data.datasource.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.entities.ExchangeRateEntity

@Dao
interface ExchangeRateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExchangeRateData(exchangeRateEntity: ExchangeRateEntity)

    @Query("SELECT * FROM exchange_rate")
    suspend fun getExchangeRateData(): List<ExchangeRateEntity>

    @Query("SELECT currencyValue FROM exchange_rate WHERE currencyName = :currencyName")
    suspend fun getSelectedCurrencyRate(currencyName: String): Double
}