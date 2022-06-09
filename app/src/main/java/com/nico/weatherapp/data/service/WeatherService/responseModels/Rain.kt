package com.nico.weatherapp.data.service.WeatherService.responseModels

import com.google.gson.annotations.SerializedName

data class Rain(
    @SerializedName("1h") val oneHour: Double
)
