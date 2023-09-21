package com.kodeco.findtime

interface TimeZoneHelper {
    fun getTimeZoneStrings(): List<String>
    fun currentTime(): String
    fun currentTimeZone(): String
    fun hoursFromTimeZone(otherTimeZoneId: String): Double
    fun getTime(timezoneId: String): String
    fun getDate(timezoneId: String): String
    fun search(startHour: Int, endHour: Int, timezoneStrings: List<String>): List<Int>
}