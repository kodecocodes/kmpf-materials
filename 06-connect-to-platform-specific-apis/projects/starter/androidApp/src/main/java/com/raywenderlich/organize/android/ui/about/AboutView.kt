package com.raywenderlich.organize.android.ui.about

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AboutView(
  onUpButtonClick: () -> Unit
) {
}

@Composable
private fun Toolbar(
  onUpButtonClick: () -> Unit,
) {
  TopAppBar(
    title = { Text(text = "About Device") },
    navigationIcon = {
      IconButton(onClick = onUpButtonClick) {
        Icon(
          imageVector = Icons.Default.ArrowBack,
          contentDescription = "Up Button",
        )
      }
    }
  )
}

@Preview(showSystemUi = true)
@Composable
private fun RowViewPreview() {
  AboutView {
  }
}
