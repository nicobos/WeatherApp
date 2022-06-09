package com.nico.weatherapp.ui.dashboard.models

import com.nico.weatherapp.R
import org.junit.Test
import com.nico.weatherapp.ui.models.WeatherRanges

class WeatherRangeTest {

    private var weatherRanges = WeatherRanges

    @Test
    fun `getImageResource should return Thunderstorm resource id for ranges 200-299`(){
        val imageResultExpected = R.drawable.thundercircleday
        for (id in 200..299){
            val result = weatherRanges.getImageResource(id)
            assert(result == imageResultExpected)
        }
    }

    @Test
    fun `getImageResource should return Drizzle resource id for ranges 300-399`(){
        val imageResultExpected = R.drawable.raincircleday
        for (id in 300..399){
            val result = weatherRanges.getImageResource(id)
            assert(result == imageResultExpected)
        }
    }

    @Test
    fun `getImageResource should return Rain resource id for ranges 500-599`(){
        val imageResultExpected = R.drawable.showerraincircleday
        for (id in 500..599){
            val result = weatherRanges.getImageResource(id)
            assert(result == imageResultExpected)
        }
    }

    @Test
    fun `getImageResource should return Rain resource id for ranges 600-699`(){
        val imageResultExpected = R.drawable.snowcircleday
        for (id in 600..699){
            val result = weatherRanges.getImageResource(id)
            assert(result == imageResultExpected)
        }
    }

    @Test
    fun `getImageResource should return Atmosphere resource id for ranges 701-799`(){
        val imageResultExpected = R.drawable.tempmaxflat
        for (id in 701..799){
            val result = weatherRanges.getImageResource(id)
            assert(result == imageResultExpected)
        }
    }

    @Test
    fun `getImageResource should return correct image resource for clouds for ids 801 - 804`(){
        val cloud1 =  R.drawable.fewcloudscircleday
        val cloud2 = R.drawable.scatteredcircleday
        val cloud3 = R.drawable.brokencloudscircleday
        val cloud4 = R.drawable.fewcloudscircleday

        val result1 = weatherRanges.getImageResource(801)
        val result2 = weatherRanges.getImageResource(802)
        val result3 = weatherRanges.getImageResource(803)
        val result4 = weatherRanges.getImageResource(804)

        assert(result1 == cloud1)
        assert(result2 == cloud2)
        assert(result3 == cloud3)
        assert(result4 == cloud4)
    }

    @Test
    fun `getImageResource should return correct image resource for Clear for id 800`(){
        val clear =  R.drawable.soleadocircleday

        val result1 = weatherRanges.getImageResource(800)

        assert(result1 == clear)
    }


}