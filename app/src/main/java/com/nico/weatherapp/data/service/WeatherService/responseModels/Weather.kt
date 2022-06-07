package com.nico.weatherapp.data.service.WeatherService.responseModels

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)
