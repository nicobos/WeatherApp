package com.nico.weatherapp.data.service.GeoService.responseModels

data class GeoCodeResponse(
    val name: String,
    val lat: Double,
    val lon: Double,
    val country: String,
    val state: String
){

    fun getFullText(): String {
        return "$name, $country, $state"
    }

}
