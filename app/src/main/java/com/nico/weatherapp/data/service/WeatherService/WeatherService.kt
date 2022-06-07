package com.nico.weatherapp.data.service.WeatherService

import com.nico.weatherapp.data.service.WeatherService.responseModels.WeatherAPIResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("data/3.0/onecall")
    suspend fun getWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("exclude") exclude: String,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "en",
        @Query("appid") appid: String
    ) : WeatherAPIResponse

}