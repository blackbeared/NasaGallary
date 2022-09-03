package com.nasa.gallary.app.utils

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

class DateUtilsKtTest {

    private val date = "2012-10-12"
    private val expectedDateString = "October 12, 2012"

    @Test
    fun testConversionFromDate() {
        val result = DateUtils.convertStringToDate(date).toString()
        val date = Calendar.getInstance()
        date.set(2012, 9, 12, 0, 0, 0)
        assertEquals(result, date.time.toString())
    }

    @Test
    fun testConvertDateToString() {
        val usDate = DateUtils.convertDateToString(date)
        assertEquals(expectedDateString, usDate)
    }
}