package com.raywenderlich.organize

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

actual object DateFormatter {
  actual fun formatEpoch(epoch: Long): String {
    val date = Date(epoch * 1000)
    return SimpleDateFormat
      .getDateTimeInstance(DateFormat.FULL, DateFormat.SHORT)
      .format(date)
  }
}