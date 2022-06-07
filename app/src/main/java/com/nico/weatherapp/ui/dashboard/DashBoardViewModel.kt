package com.nico.weatherapp.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nico.weatherapp.data.models.WeatherUI
import com.nico.weatherapp.data.repositories.contract.IGeoCodeRepo
import com.nico.weatherapp.data.repositories.contract.IWeatherRepo
import com.nico.weatherapp.data.service.Response
import com.nico.weatherapp.ui.models.Location
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(
    private val weatherRepo: IWeatherRepo,
    val geoCodeRepo: IGeoCodeRepo
) : ViewModel() {

    private val _weatherData = MutableLiveData<Response<WeatherUI?>>()
    val weatherData: LiveData<Response<WeatherUI?>> = _weatherData

    private val _currentLocation = MutableLiveData<Location>()
    val currentLocation: LiveData<Location> = _currentLocation

    init {
        getWeatherForLocation(
            lat = 33.44,
            lon = -94.04
        )
    }

    fun getWeatherForLocation(lat: Double, lon: Double) {
        viewModelScope.launch {
            weatherRepo.getWeatherAtLocation(
                lat = lat,
                lon = lon
            ).collect {
                _weatherData.value = it
            }
        }
    }

    fun setCurrentLocation(location: Location) {
        _currentLocation.postValue(location)
        getWeatherForLocation(location.lat, location.lon)
    }


}