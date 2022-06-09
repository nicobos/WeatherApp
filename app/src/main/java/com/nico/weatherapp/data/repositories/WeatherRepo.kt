package com.nico.weatherapp.data.repositories

import com.nico.weatherapp.BuildConfig
import com.nico.weatherapp.data.repositories.contract.IWeatherRepo
import com.nico.weatherapp.data.service.Response
import com.nico.weatherapp.data.service.WeatherService.WeatherService
import com.nico.weatherapp.ui.models.WeatherUI
import com.nico.weatherapp.ui.models.toWeatherUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class WeatherRepo @Inject constructor(
    private val weatherService: WeatherService
) : IWeatherRepo {

    override suspend fun getWeatherAtLocation(
        lat: Double,
        lon: Double
    ): Flow<Response<WeatherUI?>> =
        flow {
            val exclude = "minutely,daily,alerts"
            val response = weatherService.getWeatherData(
                lat = lat,
                lon = lon,
                appid = BuildConfig.weather_api_key,
                exclude = exclude
            )

            emit(Response.Success(response.toWeatherUI()) as Response<WeatherUI>)

        }.catch { throwable ->
            emit(
                Response.Failure(
                    message = throwable.message,
                    throwable = throwable
                )
            )
        }.flowOn(
            Dispatchers.IO
        )
}