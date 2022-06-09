package com.nico.weatherapp.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.nico.weatherapp.ui.models.WeatherUI
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDataDao : BaseDao<WeatherUI> {

    @Query("DELETE FROM WeatherUI")
    fun clearDB()

    @Query("SELECT * FROM WeatherUI")
    fun getWeatherData(): Flow<WeatherUI>

}