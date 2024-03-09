package com.zishan.paypaycurrencyconversion.data.repo.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("refresh_schedules")
data class RefreshSchedulesModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val resource: String,
    val timestamp: Long
)