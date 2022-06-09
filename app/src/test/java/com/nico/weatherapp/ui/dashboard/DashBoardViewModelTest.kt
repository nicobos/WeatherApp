package com.nico.weatherapp.ui.dashboard

import android.os.Build
import com.nhaarman.mockitokotlin2.*
import com.nico.weatherapp.common.extensions.doIfError
import com.nico.weatherapp.common.extensions.doIfSuccess
import com.nico.weatherapp.data.repositories.LocationRepo
import com.nico.weatherapp.data.repositories.Mocks.MockLocationRepo
import com.nico.weatherapp.data.repositories.contract.IGeoCodeRepo
import com.nico.weatherapp.data.repositories.contract.ILocationRepo
import com.nico.weatherapp.data.repositories.contract.IWeatherRepo
import com.nico.weatherapp.data.service.GeoService.responseModels.GeoCodeResponse
import com.nico.weatherapp.data.service.Response
import com.nico.weatherapp.ui.models.Location
import com.nico.weatherapp.ui.models.WeatherUI
import kotlinx.coroutines.flow.flow
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
class DashBoardViewModelTest {

    @Mock
    lateinit var weatherRepo: IWeatherRepo

    @Mock
    lateinit var geoCodeRepo: IGeoCodeRepo

    private lateinit var locationRepo: ILocationRepo

    private lateinit var dashBoardViewModel: DashBoardViewModel

    @Before
    fun setUp(){
        openMocks(this)

        locationRepo = MockLocationRepo()

        dashBoardViewModel = DashBoardViewModel(weatherRepo, geoCodeRepo, locationRepo)
    }

    @Test
    fun `getWeatherForLocation should set _weatherData livedata variable with weather for that location (via doIfSuccess callback)`(){
        val lat = -33.918861
        val lon = 18.423300
        val title = "Sunny"

        runBlocking {
            whenever(weatherRepo.getWeatherAtLocation(lat, lon)).thenReturn(
                flow {
                    emit(
                        Response.Success(
                            WeatherUI(
                                temperature = 10f,
                                feels_like = 10f,
                                humidity = 10,
                                title = title,
                                description = "Sunny with clouds",
                                icon = "00",
                                weatherConditionId = 0,
                                sunrise = "12:00",
                                sunset = "17:00",
                                hourlyData = listOf(),
                                showLoading = false
                            )
                        )
                    )
                }
            )

            dashBoardViewModel.setCurrentLocation(
                Location(
                    lat = lat,
                    lon = lon,
                    name = "Cape Town"
                )
            )

        }

        dashBoardViewModel.weatherData.observeForever {
            assert(it is Response.Success)

            it.doIfSuccess { weatherUI ->
                assert(weatherUI?.showLoading == false)
                assert(weatherUI?.title.equals(title))
            }
        }
    }

    @Test
    fun `getWeatherForLocation should run doIfError on livedata if response is Response_Failure`(){
        val lat = -33.918861
        val lon = 18.423300
        val errorMessage = "Failed to load data"


        runBlocking {
            whenever(weatherRepo.getWeatherAtLocation(lat, lon)).thenReturn(
                flow {
                    emit(
                        Response.Failure(
                            message = errorMessage,
                            throwable = Throwable(
                                message = "Failed to load",
                            )
                        )
                    )
                }
            )

            dashBoardViewModel.setCurrentLocation(
                Location(
                    lat = lat,
                    lon = lon,
                    name = "Cape Town"
                )
            )

        }

        dashBoardViewModel.weatherData.observeForever {

            assert(it is Response.Failure)

            it.doIfError { error, _ ->
                error?.equals(errorMessage)?.let { it1 -> assert(it1) }
            }

        }
    }

    @Test
    fun `getWeatherForLocation should make call to getWeatherAtLocation in weather repo`(){
        val lat = -33.918861
        val lon = 18.423300

        runBlocking {
            whenever(weatherRepo.getWeatherAtLocation(lat, lon)).thenReturn(
                flow {
                    emit(
                        Response.Success(
                            WeatherUI(
                                temperature = 10f,
                                feels_like = 10f,
                                humidity = 10,
                                title = "Sunny",
                                description = "Sunny with clouds",
                                icon = "00",
                                weatherConditionId = 0,
                                sunrise = "12:00",
                                sunset = "17:00",
                                hourlyData = listOf(),
                                showLoading = false
                            )
                        )
                    )
                }
            )

            dashBoardViewModel.setCurrentLocation(
                Location(
                    lat = lat,
                    lon = lon,
                    name = "Cape Town"
                )
            )

            verify(weatherRepo).getWeatherAtLocation(lat, lon)
        }
    }

    @Test
    fun `setWeatherDataToCurrentLocation should call location repo getLocation function`(){
        val locationRepoMock = mock<LocationRepo>()
        val dashBoardViewModelLocal = DashBoardViewModel(weatherRepo, geoCodeRepo, locationRepoMock)


        dashBoardViewModelLocal.setWeatherDataToCurrentLocation()

        verify(locationRepoMock).getLocation ( any() )
    }

    @Test
    fun `setWeatherDataToCurrentLocation should make call to geoCodeRepo reverseGeocode function`(){
        val lat = -33.918861
        val lon = 18.423300

        runBlocking {
            whenever(geoCodeRepo.reverseGeocode(lat,lon)).thenReturn(
                GeoCodeResponse(
                    name = "test",
                    lat = lat,
                    lon = lon,
                    country = "test",
                    state = "test"
                ))

            dashBoardViewModel.setWeatherDataToCurrentLocation()

            verify(geoCodeRepo).reverseGeocode(lat,lon)
        }
    }

    @Test
    fun `setWeatherDataToCurrentLocation should set current location to the users current location`(){
        // Users current mock location
        val lat = -33.918861
        val lon = 18.423300

        runBlocking {
            whenever(geoCodeRepo.reverseGeocode(lat,lon)).thenReturn(
                GeoCodeResponse(
                    name = "test",
                    lat = lat,
                    lon = lon,
                    country = "test",
                    state = "test"
                ))

            dashBoardViewModel.setWeatherDataToCurrentLocation()

        }
    }

}