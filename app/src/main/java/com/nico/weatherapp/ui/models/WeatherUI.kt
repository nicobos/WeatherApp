package com.nico.weatherapp.ui.models

import com.nico.weatherapp.common.utils.DateTimeUtils
import com.nico.weatherapp.data.service.WeatherService.responseModels.WeatherAPIResponse

data class WeatherUI(
    val temperature: Float,
    val feels_like: Float,
    val humidity: Int,
    val title: String,
    val description: String,
    val icon: String,
    val weatherConditionId: Int,
    val sunrise: String,
    val sunset: String,
    val hourlyData: List<HourlyDataItem>,
    var showLoading: Boolean = false
)

fun WeatherAPIResponse.toWeatherUI(): WeatherUI =
    WeatherUI(
        temperature = this.current.temp,
        feels_like = this.current.feels_like,
        humidity = this.current.humidity,
        title = this.current.weather.first().main,
        description = this.current.weather.first().description,
        icon = this.current.weather.first().icon,
        weatherConditionId = this.current.weather.first().id,
        sunrise = DateTimeUtils.parseUnixTime(
            this.current.sunrise,
            this.timezone
        ),
        sunset = DateTimeUtils.parseUnixTime(
            this.current.sunset,
            this.timezone
        ),
        hourlyData = this.hourly.map {
            HourlyDataItem(
                time = it.dt,
                timezone = this.timezone,
                icon = it.weather.first().icon,
                temperature = it.temp,
                precipitation = it.pop
            )
        }
    )

