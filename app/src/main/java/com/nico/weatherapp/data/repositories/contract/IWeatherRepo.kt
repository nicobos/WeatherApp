package com.nico.weatherapp.data.repositories.contract

import com.nico.weatherapp.data.service.Response
import com.nico.weatherapp.ui.models.WeatherUI
import kotlinx.coroutines.flow.Flow

interface IWeatherRepo {

    suspend fun getWeatherAtLocation(lat: Double, lon: Double): Flow<Response<WeatherUI?>>

}