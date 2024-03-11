package com.zishan.paypaycurrencyconversion.data.datasource.local.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "refresh_frequency")
data class RefreshFrequencyEntity(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    val entityKey : String,
    val timeStamp : Long
)