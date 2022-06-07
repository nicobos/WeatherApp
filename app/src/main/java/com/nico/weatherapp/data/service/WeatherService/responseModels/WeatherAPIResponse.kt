package com.nico.weatherapp.data.service.WeatherService.responseModels

data class WeatherAPIResponse(
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int,
    val current: Current
)
