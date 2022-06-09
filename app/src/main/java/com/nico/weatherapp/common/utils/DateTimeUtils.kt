package com.nico.weatherapp.common.utils

import java.text.SimpleDateFormat
import java.util.*


object DateTimeUtils {

    fun parseUnixTime(time: Long, timeZone: String): String {
        val format = SimpleDateFormat("HH:mm", Locale.ENGLISH)
        format.timeZone = TimeZone.getTimeZone(timeZone)
        return format.format(time * 1000)
    }

}