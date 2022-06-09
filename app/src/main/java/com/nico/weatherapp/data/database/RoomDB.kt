package com.nico.weatherapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nico.weatherapp.data.database.converters.HourlyDataTC
import com.nico.weatherapp.data.database.converters.WeatherUITC
import com.nico.weatherapp.data.database.dao.WeatherDataDao
import com.nico.weatherapp.ui.models.WeatherUI

@Database(
    entities = [
        WeatherUI::class
    ],
    version = 1,
    exportSchema = true
)

@TypeConverters(
    WeatherUITC::class,
    HourlyDataTC::class
)
abstract class RoomDB: RoomDatabase() {

    abstract fun weatherDataDao(): WeatherDataDao

}