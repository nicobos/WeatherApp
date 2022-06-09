package com.nico.weatherapp.data.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(obj: T): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(obj: List<@JvmSuppressWildcards T>): List<Long>

    @Delete
    suspend fun deleteItem(obj: T)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateItem(obj: T): Int
}