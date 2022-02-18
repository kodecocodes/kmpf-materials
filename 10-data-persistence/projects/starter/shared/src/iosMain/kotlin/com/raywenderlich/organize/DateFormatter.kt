package com.raywenderlich.organize

import platform.Foundation.*

actual object DateFormatter {
  private val sharedFormatter = NSDateFormatter()

  init {
    sharedFormatter.dateStyle = NSDateFormatterFullStyle
    sharedFormatter.timeStyle = NSDateFormatterShortStyle
    sharedFormatter.formattingContext = NSFormattingContextStandalone
  }

  actual fun formatEpoch(epoch: Long): String {
    return sharedFormatter.stringFromDate(
      NSDate.dateWithTimeIntervalSince1970(epoch.toDouble())
    )
  }
}