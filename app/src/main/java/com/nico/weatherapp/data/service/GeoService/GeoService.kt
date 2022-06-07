package com.nico.weatherapp.data.service.GeoService

import com.nico.weatherapp.data.service.GeoService.responseModels.GeoCodeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoService {

    @GET("geo/1.0/direct")
    suspend fun geocodeLocationName(
        @Query("q") query: String,
        @Query("appid") appid: String,
        @Query("limit") limit: Int = 5
    ): List<GeoCodeResponse>

    @GET("geo/1.0/reverse")
    suspend fun reverseGeocode(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("limit") limit: Int = 5,
        @Query("appid") appid: String
    ): List<GeoCodeResponse>


}