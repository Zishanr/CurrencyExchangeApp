package com.zishan.paypaycurrencyconversion.data.datasource.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.entities.RefreshFrequencyEntity


@Dao
interface RefreshFrequencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTimeStamp(refreshFrequencyEntity: RefreshFrequencyEntity)

    @Query("SELECT timeStamp FROM refresh_frequency WHERE entityKey = :entityKey")
    suspend fun getTimeStamp(entityKey: String): Long?
}