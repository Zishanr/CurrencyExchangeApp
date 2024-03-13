package com.zishan.paypaycurrencyconversion.data.datasource.local.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zishan.paypaycurrencyconversion.utils.PayPayConstant.DBConstant.CURRENCIES_TABLE

@Entity(tableName = CURRENCIES_TABLE)
data class CurrencyEntity(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    val currencyName: String,
    val currencyValue: String,
)