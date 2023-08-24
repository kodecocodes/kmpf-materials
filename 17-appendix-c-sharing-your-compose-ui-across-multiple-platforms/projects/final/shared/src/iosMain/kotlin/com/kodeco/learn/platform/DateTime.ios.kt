package com.kodeco.learn.platform

import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter

public actual fun formatDate(millis: Long): String {
    val dateFormatter = NSDateFormatter()
    dateFormatter.dateFormat = TIME_FORMAT

    val date = NSDate(timeIntervalSinceReferenceDate = millis.toDouble())
    return dateFormatter.stringFromDate(date)
}