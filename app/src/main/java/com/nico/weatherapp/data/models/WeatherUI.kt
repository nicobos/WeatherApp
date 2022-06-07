package com.nico.weatherapp.data.models

import com.nico.weatherapp.data.service.WeatherService.responseModels.WeatherAPIResponse

data class WeatherUI(
    val temperature: Double,
    val feels_like: Double,
    val humidity: Int,
    val title: String,
    val description: String,
    val icon: String,
    val weatherConditionId: Int
)

fun WeatherAPIResponse.toWeatherUI(): WeatherUI =
    WeatherUI(
        temperature = this.current.temp,
        feels_like = this.current.feels_like,
        humidity = this.current.humidity,
        title = this.current.weather.first().main,
        description = this.current.weather.first().description,
        icon = this.current.weather.first().icon,
        weatherConditionId = this.current.weather.first().id
    )

