package com.nico.weatherapp.data.repositories

import android.os.Build
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.nico.weatherapp.BuildConfig
import com.nico.weatherapp.data.repositories.contract.IWeatherRepo
import com.nico.weatherapp.data.service.WeatherService.WeatherService
import com.nico.weatherapp.data.service.WeatherService.responseModels.WeatherAPIResponse
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations.openMocks
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import retrofit2.HttpException
import retrofit2.Response

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [(Build.VERSION_CODES.P)])
class WeatherRepoTest {

    @Mock
    lateinit var weatherService: WeatherService

    lateinit var weatherRepo: IWeatherRepo

    @Before
    fun setUp() {
        openMocks(this)

        weatherRepo = WeatherRepo(weatherService)
    }

    @Test
    fun `getWeatherAtLocation should fetch weather info from the service`() {
        val lat = -33.918861
        val lon = 18.423300
        val exlude = "minutely,daily,alerts"
        val successResponse = mock<WeatherAPIResponse>()

        runBlocking {
            whenever(
                weatherService.getWeatherData(
                    lat = lat,
                    lon = lon,
                    exclude = exlude,
                    appid = BuildConfig.weather_api_key
                )
            ).thenReturn(successResponse)

            weatherRepo.getWeatherAtLocation(lat, lon).collect()

            verify(weatherService, atLeastOnce()).getWeatherData(
                lat = lat,
                lon = lon,
                exclude = exlude,
                appid = BuildConfig.weather_api_key
            )
        }
    }

    @Test
    fun `If there is an error when executing service call, an error response must be sent back`() {
        val lat = -33.918861
        val lon = 18.423300
        val exlude = "minutely,daily,alerts"


        runBlocking {
            whenever(
                weatherService.getWeatherData(
                    lat = lat,
                    lon = lon,
                    exclude = exlude,
                    appid = BuildConfig.weather_api_key
                )
            ).thenThrow(
                HttpException(
                    Response.error<Any>(
                        401,
                        "responsebody".toResponseBody("plain/text".toMediaTypeOrNull())
                    )
                )
            )

            weatherRepo.getWeatherAtLocation(
                lat = lat,
                lon = lon
            ).collect{
                assert(it is com.nico.weatherapp.data.service.Response.Failure)
            }
        }
    }
}