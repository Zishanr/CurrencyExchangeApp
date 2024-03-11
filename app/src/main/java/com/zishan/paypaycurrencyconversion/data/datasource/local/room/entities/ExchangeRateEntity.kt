package com.zishan.paypaycurrencyconversion.data.datasource.local.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exchange_rate")
data class ExchangeRateEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val currencyName: String,
    val currencyValue : Double
)