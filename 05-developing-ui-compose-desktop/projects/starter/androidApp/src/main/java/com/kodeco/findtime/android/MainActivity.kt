package com.kodeco.findtime.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.res.stringResource
import com.kodeco.findtime.android.ui.MainView
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Napier.base(DebugAntilog())
        setContent {
            MainView {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
                    title = {
                    when (it) {
                        0 -> Text(text = stringResource(R.string.world_clocks))
                        else -> Text(text = stringResource(R.string.findmeeting))
                    }
                })
            }
        }
    }
}



