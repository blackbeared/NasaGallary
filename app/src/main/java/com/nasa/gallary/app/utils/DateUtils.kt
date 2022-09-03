package com.nasa.gallary.app.utils

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    fun convertStringToDate(dateString: String) : Date {
        return try {
            val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date: Date = format.parse(dateString)?:Calendar.getInstance().time
            date
        } catch (e: Exception) {
            e.printStackTrace()
            Calendar.getInstance().time
        }
    }

    fun convertDateToString(dateString: String): String {
        return try {
            val format1 = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date1 = format1.parse(dateString)

            val format2 = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
            val date2 = date1?.let { format2.format(it) }
            date2?:dateString
        } catch (e: Exception) {
            e.printStackTrace()
            dateString
        }
    }
}