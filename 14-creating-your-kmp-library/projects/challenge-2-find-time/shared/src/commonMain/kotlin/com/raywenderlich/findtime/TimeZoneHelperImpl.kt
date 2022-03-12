package com.raywenderlich.findtime

import com.raywenderlich.learn.logger.Logger
import kotlinx.datetime.*
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

private const val TAG = "TimeZoneHelperImpl"

class TimeZoneHelperImpl: TimeZoneHelper {
    override fun getTimeZoneStrings(): List<String> {
        return TimeZone.availableZoneIds.sorted()
    }

    override fun currentTime(): String {
        val currentMoment: Instant = Clock.System.now()
        val dateTime: LocalDateTime = currentMoment.toLocalDateTime(TimeZone.currentSystemDefault())
        return formatDateTime(dateTime)
    }

    override fun getTime(timezoneId: String): String {
        val timezone = TimeZone.of(timezoneId)
        val currentMoment: Instant = Clock.System.now()
        val dateTime: LocalDateTime = currentMoment.toLocalDateTime(timezone)
        return formatDateTime(dateTime)
    }

    override fun getDate(timezoneId: String): String {
        val timezone = TimeZone.of(timezoneId)
        val currentMoment: Instant = Clock.System.now()
        val dateTime: LocalDateTime = currentMoment.toLocalDateTime(timezone)
        return "${dateTime.dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() }}, " +
                "${dateTime.month.name.lowercase().replaceFirstChar { it.uppercase() }} ${dateTime.date.dayOfMonth}"
    }

    override fun currentTimeZone(): String {
        val currentTimeZone = TimeZone.currentSystemDefault()
        return currentTimeZone.toString()
    }

    override fun hoursFromTimeZone(otherTimeZoneId: String): Double {
        val currentTimeZone = TimeZone.currentSystemDefault()
        val currentUTCInstant: Instant = Clock.System.now()
        // Date time in other timezone
        val otherTimeZone = TimeZone.of(otherTimeZoneId)
        val currentDateTime: LocalDateTime = currentUTCInstant.toLocalDateTime(currentTimeZone)
        val currentOtherDateTime: LocalDateTime = currentUTCInstant.toLocalDateTime(otherTimeZone)
        return abs((currentDateTime.hour - currentOtherDateTime.hour)* 1.0)
    }

    override fun search(startHour: Int, endHour: Int, timezoneStrings: List<String>): List<Int> {
        val goodHours = mutableListOf<Int>()
        val timeRange = IntRange(max(0, startHour), min(23, endHour))
        val currentTimeZone = TimeZone.currentSystemDefault()
        for (hour in timeRange) {
            var isGoodHour = false
            for (zone in timezoneStrings) {
                val timezone = TimeZone.of(zone)
                if (timezone == currentTimeZone) {
                    continue
                }
                if (!isValid(
                        timeRange = timeRange,
                        hour = hour,
                        currentTimeZone = currentTimeZone,
                        otherTimeZone = timezone
                    )
                ) {
                    Logger.d(TAG, "Hour $hour is not valid for time range")
                    isGoodHour = false
                    break
                } else {
                    Logger.d(TAG, "Hour $hour is Valid for time range")
                    isGoodHour = true
                }
            }
            if (isGoodHour) {
                goodHours.add(hour)
            }
        }
        return goodHours
    }

    private fun isValid(
        timeRange: IntRange,
        hour: Int,
        currentTimeZone: TimeZone,
        otherTimeZone: TimeZone
    ): Boolean {
        if (hour !in timeRange) {
            return false
        }
        // Right Now in UTC time
        val currentUTCInstant: Instant = Clock.System.now()
        // Date time in other timezone
        val currentOtherDateTime: LocalDateTime = currentUTCInstant.toLocalDateTime(otherTimeZone)
        // DateTime with given hour
        val otherDateTimeWithHour = LocalDateTime(
            currentOtherDateTime.year,
            currentOtherDateTime.monthNumber,
            currentOtherDateTime.dayOfMonth,
            hour,
            0,
            0,
            0
        )
        // Convert that hour into the current timezone
        val localInstant = otherDateTimeWithHour.toInstant(currentTimeZone)
        // Convert our timezone hour to other timezone
        val convertedTime = localInstant.toLocalDateTime(otherTimeZone)
        Logger.d(TAG, "Hour $hour in Time Range ${otherTimeZone.id} is ${convertedTime.hour}")
        // see if it's in our time range
        return convertedTime.hour in timeRange
    }

    fun formatDateTime(dateTime: LocalDateTime): String {
        val stringBuilder = StringBuilder()
        var hour = dateTime.hour
        val minute = dateTime.minute
        var amPm = " am"
        // For 12
        if (hour > 12) {
            amPm = " pm"
            hour -= 12
        }
        stringBuilder.append(hour.toString())
        stringBuilder.append(":")
        if (minute < 10) {
            stringBuilder.append('0')
        }
        stringBuilder.append(minute.toString())
        stringBuilder.append(amPm)
        return stringBuilder.toString()
    }
}