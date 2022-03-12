package com.raywenderlich.findtime.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.ui.res.stringResource
import com.raywenderlich.compose.ui.MainView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainView {
                TopAppBar(title = {
                    when (it) {
                        0 -> Text(text = stringResource(R.string.world_clocks))
                        else -> Text(text = stringResource(R.string.findmeeting))
                    }
                })
            }
        }
    }
}
