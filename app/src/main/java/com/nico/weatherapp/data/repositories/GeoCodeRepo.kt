package com.nico.weatherapp.data.repositories

import com.nico.weatherapp.BuildConfig
import com.nico.weatherapp.data.repositories.contract.IGeoCodeRepo
import com.nico.weatherapp.data.service.GeoService.GeoService
import com.nico.weatherapp.data.service.GeoService.responseModels.GeoCodeResponse
import javax.inject.Inject

class GeoCodeRepo @Inject constructor(
    val geoService: GeoService
): IGeoCodeRepo {

    override suspend fun geocodeLocationName(query: String): List<GeoCodeResponse> {
        return geoService.geocodeLocationName(
            query = query,
            appid = BuildConfig.weather_api_key,
            limit = 5
        )
    }
}