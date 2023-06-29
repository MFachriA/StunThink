package com.projectAnya.stunthink.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DateUtils {
    private const val DATE_FORMAT_PATTERN = "yyyy-MM-dd"
    private const val DATE_WITH_TIME_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    private const val DATE_INDONESIA_FORMAT_PATTERN = "dd MMMM yyyy"
    private const val DATE_INDONESIA_WITH_TIME_FORMAT_PATTERN = "EEEE dd MMMM yyyy, HH:mm 'WIB'"
    private val LOCALE_INDONESIA = Locale("id", "ID")

    private val dateFormat = SimpleDateFormat(DATE_FORMAT_PATTERN, LOCALE_INDONESIA)
    private val dateWithTimeFormat = SimpleDateFormat(DATE_WITH_TIME_FORMAT_PATTERN, LOCALE_INDONESIA)
    private val indonesianDateFormat = SimpleDateFormat(DATE_INDONESIA_FORMAT_PATTERN, LOCALE_INDONESIA)
    private val indonesianDateWithTimeFormat =
        SimpleDateFormat(DATE_INDONESIA_WITH_TIME_FORMAT_PATTERN, LOCALE_INDONESIA)

    init {
        dateWithTimeFormat.timeZone = TimeZone.getTimeZone("WIB")
    }

    fun formatDateToIndonesianDate(dateString: String): String {
        return try {
            val date = dateFormat.parse(dateString)
            indonesianDateFormat.format(date ?: "")
        } catch (e: Exception) {
            ""
        }
    }

    fun formatDateTimeToIndonesianDate(dateString: String): String {
        return try {
            val date = dateWithTimeFormat.parse(dateString)
            indonesianDateFormat.format(date ?: "")
        } catch (e: Exception) {
            ""
        }
    }

    fun formatDateTimeToIndonesianTimeDate(dateString: String): String {
        return try {
            val date = dateWithTimeFormat.parse(dateString)
            indonesianDateWithTimeFormat.format(date ?: "")
        } catch (e: Exception) {
            ""
        }
    }

    fun isDatePassed(dateString: String): Boolean {
        val currentDate = Date()
        val date = dateFormat.parse(dateString)

        return date?.after(currentDate) ?: false
    }
}