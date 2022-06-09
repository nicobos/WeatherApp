package com.nico.weatherapp.data.database.converters

import androidx.room.TypeConverter
import com.nico.weatherapp.common.extensions.fromGson
import com.nico.weatherapp.common.extensions.toGson
import com.nico.weatherapp.ui.models.WeatherUI

class WeatherUITC {

    @TypeConverter
    fun toWeatherUI(value: String) = value.fromGson(WeatherUI::class.java)

    @TypeConverter
    fun toString(value: WeatherUI) = value.toGson()

}