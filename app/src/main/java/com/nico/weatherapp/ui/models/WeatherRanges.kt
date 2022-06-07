package weather.android.dvt.co.za.weather.WeatherInfo.DataModels

import com.nico.weatherapp.R


/**
 * Created by Wolf on 03/03/2018.
 */
object WeatherRanges {
    val ThunderStorm = IntRange(200,299)
    val Drizzle = IntRange(300,399)
    val Rain = IntRange(500,599)
    val Snow = IntRange(600,699)
    val Atmosphere = IntRange(701,799)
    val Clear = 800
    val Clouds = IntRange(801,899)
    val Extreme = IntRange(900,909)
    val Extreme_additional = IntRange(910,999)

    fun getImageResource(id: Int?): Int{
        var resourceId = 0

        when (id){
        /* Thunderstorms */
            in ThunderStorm ->resourceId = R.drawable.thundercircleday
        /* Drizzle */
            in Drizzle ->resourceId = R.drawable.raincircleday
        /* Rain */
            in Rain ->resourceId = R.drawable.showerraincircleday
        /* Snow */
            in Snow ->resourceId = R.drawable.snowcircleday
        /* Atmosphere */
            in Atmosphere ->resourceId = R.drawable.tempmaxflat
        /* Clear */
            Clear -> resourceId = R.drawable.soleadocircleday
        /* Clouds */
            in Clouds -> {
                when (id){
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