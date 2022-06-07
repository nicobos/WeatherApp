package com.nico.weatherapp.data.repositories.contract

import com.nico.weatherapp.data.models.WeatherUI
import com.nico.weatherapp.data.service.Response
import kotlinx.coroutines.flow.Flow

interface IWeatherRepo {

    suspend fun getWeatherAtLocation(lat: Double, lon: Double): Flow<Response<WeatherUI?>>

}