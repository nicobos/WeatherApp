package com.nico.weatherapp.ui.models

data class HourlyDataItem(
    val time: Long,
    val timezone: String,
    val icon: String,
    val temperature: Float,
    val precipitation: Float
)
