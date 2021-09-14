package com.raywenderlich.findtime

import io.github.aakira.napier.Napier
import kotlinx.datetime.*
import kotlin.math.max
import kotlin.math.min

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
        return formatDateTime(dateTime)    }

    override fun search(startHour: Int, endHour: Int, timezoneStrings: List<String>): List<Int> {
// 1
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
// 1
        val localInstant = otherDateTimeWithHour.toInstant(currentTimeZone)
// 2
        val convertedTime = localInstant.toLocalDateTime(otherTimeZone)
        Napier.d("Hour $hour in Time Range ${otherTimeZone.id} is ${convertedTime.hour}")
// 3
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