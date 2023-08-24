package com.kodeco.learn.platform

import java.text.SimpleDateFormat
import java.util.Locale

private const val TAG = "DateTime"

public actual fun formatDate(millis: Long): String {
    return try {
        SimpleDateFormat(TIME_FORMAT, Locale.getDefault()).format(millis)
    } catch (e: Exception) {
        Logger.d(TAG, "Error while converting dates. Error: $e")
        ""
    }
}
