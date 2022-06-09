package com.nico.weatherapp.data.database.converters

import androidx.room.TypeConverter
import com.google.gson.JsonArray
import com.nico.weatherapp.common.extensions.fromGson
import com.nico.weatherapp.common.extensions.toGson
import com.nico.weatherapp.ui.models.HourlyDataItem


class HourlyDataTC {

    @TypeConverter
    fun toHourlyDataList(value: String?): List<HourlyDataItem> {
        if (value.isNullOrEmpty()) {
            return emptyList()
        }

        val jsonArray = value.fromGson(JsonArray::class.java)

        return jsonArray.map { je -> je.fromGson(HourlyDataItem::class.java) }
    }

    @TypeConverter
    fun toString(value: List<HourlyDataItem>) = value.toGson()

}