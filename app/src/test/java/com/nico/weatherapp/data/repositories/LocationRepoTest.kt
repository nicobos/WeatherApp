package com.nico.weatherapp.data.repositories

import android.os.Build
import com.nico.weatherapp.data.repositories.Mocks.MockLocationRepo
import com.nico.weatherapp.data.repositories.contract.ILocationRepo
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations.openMocks
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [(Build.VERSION_CODES.P)], manifest = "../app/src/test/AndroidManifest.xml")
class LocationRepoTest {

    lateinit var locationRepo: ILocationRepo

    @Before
    fun setUp() {
        openMocks(this)

        locationRepo = MockLocationRepo()
    }

    @Test
    fun `when calling getLocation return a LocationModel instance with correct lat and lon values`() {
        val lat = -33.918861
        val lon = 18.423300

        locationRepo.getLocation {
            assert(it != null)
            assert(it?.lat == lat)
            assert(it?.lon == lon)
        }
    }

    @Test
    fun `when calling getLocation assertion should be false if testdata changes`() {
        val lat = -35.918861
        val lon = 20.423300

        locationRepo.getLocation {
            assert(it != null)
            assert(it?.lat != lat)
            assert(it?.lon != lon)
        }
    }


}