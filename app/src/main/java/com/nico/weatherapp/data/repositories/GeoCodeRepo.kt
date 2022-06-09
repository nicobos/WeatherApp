package com.nico.weatherapp.data.repositories

import com.nico.weatherapp.BuildConfig
import com.nico.weatherapp.data.repositories.contract.IGeoCodeRepo
import com.nico.weatherapp.data.service.GeoService.GeoService
import com.nico.weatherapp.data.service.GeoService.responseModels.GeoCodeResponse
import javax.inject.Inject

class GeoCodeRepo @Inject constructor(
    private val geoService: GeoService
): IGeoCodeRepo {

    override suspend fun geocodeLocationName(query: String): List<GeoCodeResponse> {
        return geoService.geocodeLocationName(
            query = query,
            appid = BuildConfig.weather_api_key,
            limit = 5
        )
    }

    override suspend fun reverseGeocode(lat: Double, lon: Double): GeoCodeResponse {
        return geoService.reverseGeocode(
            lat = lat,
            lon = lon,
            appid = BuildConfig.weather_api_key,
            limit = 5
        ).first()
    }
}