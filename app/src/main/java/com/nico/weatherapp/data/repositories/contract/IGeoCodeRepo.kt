package com.nico.weatherapp.data.repositories.contract

import com.nico.weatherapp.data.service.GeoService.responseModels.GeoCodeResponse

interface IGeoCodeRepo {

    suspend fun geocodeLocationName(query: String): List<GeoCodeResponse>

}