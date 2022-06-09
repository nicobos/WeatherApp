package com.nico.weatherapp.common.utils

import org.junit.Test

class DateTimeUtilsTests {

    private var systemUnderTest: DateTimeUtils = DateTimeUtils

    @Test
    fun `parseUnixTime should return the correct time formatted for the specific timezone`() {
        val expectedResult = "12:18"
        val result = systemUnderTest.parseUnixTime(1654683485, "Etc/GMT-2")

        assert(result == expectedResult)

    }

    @Test
    fun `parseUnixTime should will return the wrong date if the timezone is wrong`() {
        val expectedResult = "12:18"
        val result = systemUnderTest.parseUnixTime(1654683485, "Etc/GMT-1")

        assert(result != expectedResult)
    }

}