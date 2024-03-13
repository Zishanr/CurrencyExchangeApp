package com.zishan.paypaycurrencyconversion.data.datasource.local.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zishan.paypaycurrencyconversion.utils.PayPayConstant.DBConstant.REFRESH_FREQUENCY_TABLE

@Entity(tableName = REFRESH_FREQUENCY_TABLE)
data class RefreshFrequencyEntity(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    val entityKey : String,
    val timeStamp : Long
)