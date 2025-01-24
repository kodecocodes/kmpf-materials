package com.kodeco.findtime.android.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.kodeco.findtime.android.MyApplicationTheme

sealed class Screen(val title: String) {
    data object TimeZonesScreen : Screen("Timezones")
    data object FindTimeScreen : Screen("Find Time")
}

data class BottomItem(
    val route: String,
    val icon: ImageVector,
    val iconContentDescription: String
)

val bottomNavigationItems = listOf(
    BottomItem(
        Screen.TimeZonesScreen.title,
        Icons.Filled.Language,
        "Timezones"
    ),
    BottomItem(
        Screen.FindTimeScreen.title,
        Icons.Filled.Place,
        "Find Time"
    )
)


@Composable
// 2
fun MainView(actionBarFun: topBarFun = { EmptyComposable() }) {
    // 3
    val showAddDialog = remember { mutableStateOf(false) }
    // 4
    val currentTimezoneStrings = remember { SnapshotStateList<String>() }
    // 5
    val selectedIndex = remember { mutableIntStateOf(0)}
    // 6
    MyApplicationTheme {
        Scaffold(
            topBar = {
                actionBarFun(selectedIndex.intValue)
            },
            floatingActionButton = {
                if (selectedIndex.intValue == 0) {
                    // 1
                    FloatingActionButton(
                        // 2
                        modifier = Modifier
                            .padding(16.dp),
                        shape = FloatingActionButtonDefaults.largeShape,
                        containerColor = MaterialTheme.colorScheme.secondary,
                        // 3
                        onClick = {
                            showAddDialog.value = true
                        }
                    ) {
                        // 4
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Timezone"
                        )
                    }
                }
            },
            bottomBar = {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    // 2
                    bottomNavigationItems.forEachIndexed { i, bottomNavigationItem ->
                        // 3
                        NavigationBarItem(
                            colors =  NavigationBarItemDefaults.colors(
                                selectedIconColor = Color.White,
                                selectedTextColor = Color.White,
                                unselectedIconColor = Color.Black,
                                unselectedTextColor = Color.Black,
                                indicatorColor = MaterialTheme.colorScheme.primary,
                            ),
                            label = {
                                Text(bottomNavigationItem.route, style = MaterialTheme.typography.bodyMedium)
                            },
                            // 4
                            icon = {
                                Icon(
                                    bottomNavigationItem.icon,
                                    contentDescription = bottomNavigationItem.iconContentDescription
                                )
                            },
                            // 5
                            selected = selectedIndex.intValue == i,
                            // 6
                            onClick = {
                                selectedIndex.intValue = i
                            }
                        )
                    }
                }
            }
        ) { padding ->
            Box(modifier = Modifier.padding(padding)) {
                if (showAddDialog.value) {
                    AddTimeZoneDialog(
                        // 2
                        onAdd = { newTimezones ->
                            showAddDialog.value = false
                            for (zone in newTimezones) {
                                // 3
                                if (!currentTimezoneStrings.contains(zone)) {
                                    currentTimezoneStrings.add(zone)
                                }
                            }
                        },
                        onDismiss = {
                            // 4
                            showAddDialog.value = false
                        },
                    )
                }
                when (selectedIndex.intValue	) {
                    0 -> TimeZoneScreen(currentTimezoneStrings)
                     1 -> FindMeetingScreen(currentTimezoneStrings)
                }
            }
        }
    }
}
