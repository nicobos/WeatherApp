package com.nico.weatherapp.data.service.WeatherService.responseModels

data class WeatherAPIResponse(
    val lat: Float,
    val lon: Float,
    val timezone: String,
    val timezone_offset: Int,
    val current: WeatherInfo,
    val hourly: List<WeatherInfo>
)
