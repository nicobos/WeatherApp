package com.nico.weatherapp.data.repositories.Mocks

import android.location.Location
import android.location.LocationManager
import com.nico.weatherapp.data.repositories.contract.ILocationRepo
import com.nico.weatherapp.ui.models.LocationModel


class MockLocationRepo : ILocationRepo {

    override fun getLocation(result: (LocationModel?) -> Unit) {
        val mockLocation = getMockLocation()
        result(
            LocationModel(
                lat = mockLocation.latitude,
                lon = mockLocation.longitude
            )
        )
    }

    private fun getMockLocation(): Location {
        val lat = -33.918861
        val lon = 18.423300

        val dummyLocation = Location(LocationManager.GPS_PROVIDER)
        dummyLocation.latitude = lat
        dummyLocation.longitude = lon

        return dummyLocation
    }

}