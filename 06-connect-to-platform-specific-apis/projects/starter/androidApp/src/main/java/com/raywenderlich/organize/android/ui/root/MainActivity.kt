package com.raywenderlich.organize.android.ui.root

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.raywenderlich.organize.android.ui.theme.RWTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      RWTheme {
        AppScaffold()
      }
    }
  }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
  RWTheme {
    AppScaffold()
  }
}