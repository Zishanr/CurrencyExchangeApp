package com.zishan.paypaycurrencyconversion.data.datasource.local.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currencies")
data class CurrencyEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val currencyName: String,
    val currencyValue: String,
)