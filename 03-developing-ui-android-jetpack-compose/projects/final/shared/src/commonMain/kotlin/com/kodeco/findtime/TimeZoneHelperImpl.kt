package com.kodeco.findtime

import io.github.aakira.napier.Napier
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class TimeZoneHelperImpl: TimeZoneHelper {
    override fun getTimeZoneStrings(): List<String> {
        return TimeZone.availableZoneIds.sorted()
    }

    override fun currentTime(): String {
        // 1
        val currentMoment: Instant = Clock.System.now()
        // 2
        val dateTime: LocalDateTime = currentMoment.toLocalDateTime(TimeZone.currentSystemDefault())
        // 3
        return formatDateTime(dateTime)
    }

    override fun currentTimeZone(): String {
        val currentTimeZone = TimeZone.currentSystemDefault()
        return currentTimeZone.toString()
    }

    override fun hoursFromTimeZone(otherTimeZoneId: String): Double {
        // 1
        val currentTimeZone = TimeZone.currentSystemDefault()
        // 2
        val currentUTCInstant: Instant = Clock.System.now()
        // Date time in other timezone
        // 3
        val otherTimeZone = TimeZone.of(otherTimeZoneId)
        // 4
        val currentDateTime: LocalDateTime = currentUTCInstant.toLocalDateTime(currentTimeZone)
        // 5
        val currentOtherDateTime: LocalDateTime = currentUTCInstant.toLocalDateTime(otherTimeZone)
        // 6
        return abs((currentDateTime.hour - currentOtherDateTime.hour) * 1.0)
    }

    override fun getTime(timezoneId: String): String {
        // 1
        val timezone = TimeZone.of(timezoneId)
        // 2
        val currentMoment: Instant = Clock.System.now()
        // 3
        val dateTime: LocalDateTime = currentMoment.toLocalDateTime(timezone)
        // 4
        return formatDateTime(dateTime)
    }

    override fun getDate(timezoneId: String): String {
        val timezone = TimeZone.of(timezoneId)
        val currentMoment: Instant = Clock.System.now()
        val dateTime: LocalDateTime = currentMoment.toLocalDateTime(timezone)
        // 1
        return "${dateTime.dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() }}, " +
                "${dateTime.month.name.lowercase().replaceFirstChar { it.uppercase() }} ${dateTime.date.dayOfMonth}"
    }

    override fun search(startHour: Int, endHour: Int, timezoneStrings: List<String>): List<Int> {
        val goodHours = mutableListOf<Int>()
// 2
        val timeRange = IntRange(max(0, startHour), min(23, endHour))
// 3
        val currentTimeZone = TimeZone.currentSystemDefault()
// 4
        for (hour in timeRange) {
            var isGoodHour = false
            // 5
            for (zone in timezoneStrings) {
                val timezone = TimeZone.of(zone)
                // 6
                if (timezone == currentTimeZone) {
                    continue
                }
                // 7
                if (!isValid(
                        timeRange = timeRange,
                        hour = hour,
                        currentTimeZone = currentTimeZone,
                        otherTimeZone = timezone
                    )
                ) {
                    Napier.d("Hour $hour is not valid for time range")
                    isGoodHour = false
                    break
                } else {
                    Napier.d("Hour $hour is Valid for time range")
                    isGoodHour = true
                }
            }
            // 8
            if (isGoodHour) {
                goodHours.add(hour)
            }
        }
// 9
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
// 1
        val currentUTCInstant: Instant = Clock.System.now()
// 2
        val currentOtherDateTime: LocalDateTime = currentUTCInstant.toLocalDateTime(otherTimeZone)
// 3
        val otherDateTimeWithHour = LocalDateTime(
            currentOtherDateTime.year,
            currentOtherDateTime.monthNumber,
            currentOtherDateTime.dayOfMonth,
            hour,
            0,
            0,
            0
        )
        val localInstant = otherDateTimeWithHour.toInstant(currentTimeZone)
// 2
        val convertedTime = localInstant.toLocalDateTime(otherTimeZone)
        Napier.d("Hour $hour in Time Range ${otherTimeZone.id} is ${convertedTime.hour}")
// 3
        return convertedTime.hour in timeRange
  }

    fun formatDateTime(dateTime: LocalDateTime): String {
        // 1
        val stringBuilder = StringBuilder()
        // 2
        val minute = dateTime.minute
        var hour = dateTime.hour % 12
        if (hour == 0) hour = 12
        // 3
        val amPm = if (dateTime.hour < 12) " am" else " pm"
        // 4
        stringBuilder.append(hour.toString())
        stringBuilder.append(":")
        // 5
        if (minute < 10) {
            stringBuilder.append('0')
        }
        stringBuilder.append(minute.toString())
        stringBuilder.append(amPm)
        // 6
        return stringBuilder.toString()
    }
}