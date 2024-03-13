package com.zishan.paypaycurrencyconversion.data.datasource.local.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zishan.paypaycurrencyconversion.utils.PayPayConstant.DBConstant.EXCHANGE_RATE_TABLE

@Entity(tableName = EXCHANGE_RATE_TABLE)
data class ExchangeRateEntity(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    val currencyName: String,
    val currencyValue : Double
)