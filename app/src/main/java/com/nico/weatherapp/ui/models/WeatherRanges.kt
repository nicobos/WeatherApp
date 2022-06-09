package com.nico.weatherapp.ui.models

import com.nico.weatherapp.R

object WeatherRanges {
    private val ThunderStorm = IntRange(200, 299)
    private val Drizzle = IntRange(300, 399)
    private val Rain = IntRange(500, 599)
    private val Snow = IntRange(600, 699)
    private val Atmosphere = IntRange(701, 799)
    private const val Clear = 800
    private val Clouds = IntRange(801, 899)

    fun getImageResource(id: Int?): Int {
        var resourceId = 0

        when (id) {

            in ThunderStorm -> resourceId = R.drawable.thundercircleday

            in Drizzle -> resourceId = R.drawable.raincircleday

            in Rain -> resourceId = R.drawable.showerraincircleday

            in Snow -> resourceId = R.drawable.snowcircleday

            in Atmosphere -> resourceId = R.drawable.tempmaxflat

            Clear -> resourceId = R.drawable.soleadocircleday

            in Clouds -> {
                when (id) {
                    801 -> resourceId = R.drawable.fewcloudscircleday
                    802 -> resourceId = R.drawable.scatteredcircleday
                    803 -> resourceId = R.drawable.brokencloudscircleday
                    804 -> resourceId = R.drawable.fewcloudscircleday
                }

            }
        }
        return resourceId
    }
}