package com.raywenderlich.findtime.android.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.raywenderlich.findtime.TimeZoneHelper
import com.raywenderlich.findtime.TimeZoneHelperImpl
import kotlinx.coroutines.delay

const val timeMillis = 1000 * 60L // 1 second

@Composable
fun TimeZoneScreen(
    currentTimezoneStrings: SnapshotStateList<String>,
    padding: PaddingValues
) {
    val timezoneHelper: TimeZoneHelper = TimeZoneHelperImpl()
    val listState = rememberLazyListState()
    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
    ) {
        var time by remember { mutableStateOf(timezoneHelper.currentTime()) }
        LaunchedEffect(Unit) {
            while (true) {
                time = timezoneHelper.currentTime()
                delay(timeMillis) // Every minute
            }
        }
        LocalTimeCard(
            city = timezoneHelper.currentTimeZone(),
            time = time, date = timezoneHelper.getDate(timezoneHelper.currentTimeZone())
        )
        Spacer(modifier = Modifier.size(16.dp))
        LazyColumn(
            state = listState,
        ) {
            items(currentTimezoneStrings,
                key = { timezone ->
                    timezone
                }) { timezoneString ->
                AnimatedSwipeDismiss(
                    item = timezoneString,
                    background = { _ ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .height(50.dp)
                                .background(Color.Red)
                                .padding(
                                    start = 20.dp,
                                    end = 20.dp
                                )
                        ) {
                            val alpha = 1f
                            Icon(
                                Icons.Filled.Delete,
                                contentDescription = "Delete",
                                modifier = Modifier
                                    .align(Alignment.CenterEnd),
                                tint = Color.White.copy(alpha = alpha)
                            )
                        }
                    },
                    content = {
                        TimeCard(
                            timezone = timezoneString,
                            hours = timezoneHelper.hoursFromTimeZone(timezoneString),
                            time = timezoneHelper.getTime(timezoneString),
                            date = timezoneHelper.getDate(timezoneString)
                        )
                    },
                    onDismiss = { zone ->
                        if (currentTimezoneStrings.contains(zone)) {
                            currentTimezoneStrings.remove(zone)
                        }
                    }
                )
            }
        }
    }
}