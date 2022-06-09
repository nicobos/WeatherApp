package com.nico.weatherapp.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nico.weatherapp.common.extensions.doIfSuccess
import com.nico.weatherapp.data.repositories.contract.IGeoCodeRepo
import com.nico.weatherapp.data.repositories.contract.ILocationRepo
import com.nico.weatherapp.data.repositories.contract.IWeatherRepo
import com.nico.weatherapp.data.service.Response
import com.nico.weatherapp.ui.models.Location
import com.nico.weatherapp.ui.models.WeatherUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(
    private val weatherRepo: IWeatherRepo,
    private val geoCodeRepo: IGeoCodeRepo,
    private val locationRepo: ILocationRepo
) : ViewModel() {

    private val _weatherData = MutableLiveData<Response<WeatherUI?>>()
    val weatherData: LiveData<Response<WeatherUI?>> = _weatherData

    private val _currentLocation = MutableLiveData<Location>()
    val currentLocation: LiveData<Location> = _currentLocation

    private val _showLoading = MutableLiveData<Boolean>(false)
    val showLoading: LiveData<Boolean> = _showLoading

    fun getGeoCodeRepo(): IGeoCodeRepo {
        return geoCodeRepo
    }

    private fun getWeatherForLocation(lat: Double, lon: Double) {
        viewModelScope.launch {
            weatherRepo.getWeatherAtLocation(
                lat = lat,
                lon = lon
            ).collect {
                _weatherData.value = it
                _showLoading.value = false
            }
        }
    }


    fun setCurrentLocation(location: Location) {
        _showLoading.value = true
        _currentLocation.value = location
        getWeatherForLocation(location.lat, location.lon)
    }

    private fun getLocationFromLatLon(lat: Double, lon: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = geoCodeRepo.reverseGeocode(
                lat = lat,
                lon = lon
            )

            withContext(Dispatchers.Main) {
                setCurrentLocation(
                    Location(
                        lat = result.lat,
                        lon = result.lon,
                        name = result.name
                    )
                )
            }
        }
    }

    fun setWeatherDataToCurrentLocation() {
        locationRepo.getLocation {
            if (it != null) {
                getLocationFromLatLon(
                    lat = it.lat,
                    lon = it.lon
                )
            } else {
                // TODO SHOW ERROR
            }
        }
    }


}