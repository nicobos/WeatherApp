package com.nico.weatherapp.data.repositories

import android.annotation.SuppressLint
import com.google.android.gms.location.FusedLocationProviderClient
import com.nico.weatherapp.ui.models.LocationModel
import com.nico.weatherapp.data.repositories.contract.ILocationRepo
import javax.inject.Inject

class LocationRepo @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient
) : ILocationRepo {

    @SuppressLint("MissingPermission")
    override fun getLocation(result: (LocationModel?) -> Unit) {

        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            result(
                LocationModel(
                    lat = location.latitude,
                    lon = location.longitude
                )
            )
        }
    }
}
