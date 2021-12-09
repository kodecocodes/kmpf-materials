package com.raywenderlich.findtime.android.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.raywenderlich.findtime.TimeZoneHelper
import com.raywenderlich.findtime.TimeZoneHelperImpl

@Composable
fun FindMeetingScreen(
    timezoneStrings: List<String>
) {
    val listState = rememberLazyListState()
    // 8am
    val startTime = remember {
        mutableStateOf(8)
    }
    // 5pm
    val endTime = remember {
        mutableStateOf(17)
    }
    val selectedTimeZones = remember {
        val selected = SnapshotStateMap<Int, Boolean>()
        for (i in 0..timezoneStrings.size-1) selected[i] = true
        selected
    }
    val timezoneHelper: TimeZoneHelper = TimeZoneHelperImpl()
    val showMeetingDialog = remember { mutableStateOf(false) }
    val meetingHours = remember { SnapshotStateList<Int>() }

    if (showMeetingDialog.value) {
        MeetingDialog(
            hours = meetingHours,
            onDismiss = {
                showMeetingDialog.value = false
            }
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally),
            text = "Time Range",
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.size(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 4.dp)
                .wrapContentWidth(Alignment.CenterHorizontally),

            ) {
            Spacer(modifier = Modifier.size(16.dp))
            numberTimeCard("Start", startTime)
            Spacer(modifier = Modifier.size(32.dp))
            numberTimeCard("End", endTime)
        }
        Spacer(modifier = Modifier.size(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 4.dp)

        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally),
                text = "Time Zones",
                style = MaterialTheme.typography.h6
            )
        }
        Spacer(modifier = Modifier.size(16.dp))
        LazyColumn(
            modifier = Modifier
                .weight(0.6F)
                .fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            state = listState,

            ) {
            itemsIndexed(timezoneStrings) { i, timezone ->
                Surface(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),

                    ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {
                        Checkbox(checked = isSelected(selectedTimeZones, i),
                            onCheckedChange = {
                                selectedTimeZones[i] = it
                            })
                        Text(timezone, modifier = Modifier.align(Alignment.CenterVertically))
                    }
                }
            }
        }
        Spacer(Modifier.weight(0.1f))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.3F)
                .wrapContentWidth(Alignment.CenterHorizontally)
                .padding(start = 4.dp, end = 4.dp)

        ) {
            OutlinedButton(
                colors = ButtonDefaults.outlinedButtonColors(backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.secondaryVariant),
                onClick = {
                meetingHours.clear()
                meetingHours.addAll(
                    timezoneHelper.search(
                        startTime.value,
                        endTime.value,
                        getSelectedTimeZones(timezoneStrings, selectedTimeZones)
                    )
                )
                showMeetingDialog.value = true
            }) {
                Text("Search")
            }
        }
        Spacer(Modifier.size(16.dp))
    }
}

fun getSelectedTimeZones(
    timezoneStrings: List<String>,
    selectedStates: Map<Int, Boolean>
): List<String> {
    val selectedTimezones = mutableListOf<String>()
    selectedStates.keys.map {
        val timezone = timezoneStrings[it]
        if (isSelected(selectedStates, it) && !selectedTimezones.contains(timezone)) {
            selectedTimezones.add(timezone)
        }
    }
    return selectedTimezones
}