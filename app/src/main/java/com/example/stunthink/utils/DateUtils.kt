package com.example.stunthink.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatDate(dateString: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))

    return try {
        val date = inputFormat.parse(dateString)
        outputFormat.format(date ?: "")
    } catch (e: Exception) {
        ""
    }
}

fun isDatePassed(dateString: String): Boolean {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    val currentDate = Date()
    val date = dateFormat.parse(dateString)

    return date?.after(currentDate) ?: false
}