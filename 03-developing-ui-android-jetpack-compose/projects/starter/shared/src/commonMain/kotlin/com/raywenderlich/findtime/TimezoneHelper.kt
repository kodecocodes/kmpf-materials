package com.raywenderlich.findtime

interface TimeZoneHelper {
    fun getTimeZoneStrings(): List<String>
    fun currentTime(): String
    fun getTime(timezoneId: String): String
    fun search(startHour: Int, endHour: Int, timezoneStrings: List<String>): List<Int>
}