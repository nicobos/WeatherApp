package com.nico.weatherapp.data.repositories.contract

import com.nico.weatherapp.ui.models.LocationModel

interface ILocationRepo {

    fun getLocation(result: (LocationModel?) -> Unit)

}