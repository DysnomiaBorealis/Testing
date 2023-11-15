package com.wastehub.user.common.utils

import java.text.SimpleDateFormat

object DateUtil {
    fun formatDate(originalDate: String): String {
        val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val date = originalFormat.parse(originalDate)
        val outputFormat = SimpleDateFormat("dd/MM/yyyy")
        return outputFormat.format(date)
    }
}