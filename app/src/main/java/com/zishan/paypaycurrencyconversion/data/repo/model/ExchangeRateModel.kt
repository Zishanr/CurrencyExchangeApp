package com.zishan.paypaycurrencyconversion.data.repo.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exchange_rates")
data class ExchangeRateModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val code: String,
    val rate: Double
)