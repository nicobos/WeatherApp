package com.nico.weatherapp.data.repositories

import android.os.Build
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.nico.weatherapp.BuildConfig
import com.nico.weatherapp.data.service.GeoService.GeoService
import com.nico.weatherapp.data.service.GeoService.responseModels.GeoCodeResponse
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations.openMocks
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [(Build.VERSION_CODES.P)])
class GeoCodeRepoTest {

    @Mock
    lateinit var geoService: GeoService

    private lateinit var repo: GeoCodeRepo

    @Before
    fun setUp(){
        openMocks(this)

        repo = GeoCodeRepo(geoService)
    }

    @Test
    fun `getCodeLocationName should make a service call to geocode location name`(){
        val query = "Cape Town"
        val successResponse = listOf(mock<GeoCodeResponse>())

        runBlocking {
            whenever(geoService.geocodeLocationName(
                query = query,
                appid = BuildConfig.weather_api_key,
                limit = 5
            )).thenReturn(successResponse)

            repo.geocodeLocationName(query)

            verify(geoService, atLeastOnce()).geocodeLocationName(
                query = query,
                appid = BuildConfig.weather_api_key,
                limit = 5
            )
        }
    }

    @Test
    fun `reverGeocode should make a service call to reverse geocode`(){
        val lat = -33.918861
        val lon = 18.423300
        val successResponse = listOf(mock<GeoCodeResponse>())

        runBlocking {
            whenever(geoService.reverseGeocode(
                lat = lat,
                lon = lon,
                appid = BuildConfig.weather_api_key,
                limit = 5
            )).thenReturn(successResponse)

            repo.reverseGeocode(
                lat = lat,
                lon = lon
            )

            verify(geoService, atLeastOnce()).reverseGeocode(
                lat = lat,
                lon = lon,
                appid = BuildConfig.weather_api_key,
                limit = 5
            )
        }
    }

}