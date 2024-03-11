package com.zishan.paypaycurrencyconversion.data.datasource.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zishan.paypaycurrencyconversion.data.datasource.local.room.entities.CurrencyEntity

@Dao
interface CurrencyDao {

    //TODO Read
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(currencyEntity: CurrencyEntity)

    @Query("SELECT * FROM currencies")
    suspend fun getAllData():List<CurrencyEntity>
}