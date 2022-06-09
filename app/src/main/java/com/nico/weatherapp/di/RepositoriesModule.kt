package com.nico.weatherapp.di

import com.nico.weatherapp.data.repositories.GeoCodeRepo
import com.nico.weatherapp.data.repositories.LocationRepo
import com.nico.weatherapp.data.repositories.WeatherRepo
import com.nico.weatherapp.data.repositories.contract.IGeoCodeRepo
import com.nico.weatherapp.data.repositories.contract.ILocationRepo
import com.nico.weatherapp.data.repositories.contract.IWeatherRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoriesModule {

    @Binds
    fun bindWeatherRepository(impl: WeatherRepo): IWeatherRepo

    @Binds
    fun bindGeoCodeRepository(impl: GeoCodeRepo): IGeoCodeRepo

    @Binds
    fun bindLocationRepository(impl: LocationRepo): ILocationRepo

}