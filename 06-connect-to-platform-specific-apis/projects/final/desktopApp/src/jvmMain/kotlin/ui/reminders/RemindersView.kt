package ui.reminders

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun RemindersView(
  onAboutIconClick: () -> Unit,
) {
  Column {
    Toolbar(onAboutIconClick = onAboutIconClick)
    ContentView()
  }
}

@Composable
private fun Toolbar(
  onAboutIconClick: () -> Unit,
) {
  TopAppBar(
    title = { Text(text = "Reminders") },
    actions = {
      IconButton(onClick = onAboutIconClick) {
        Icon(
          imageVector = Icons.Outlined.Info,
          contentDescription = "About Device Button",
        )
      }
    }
  )
}

@Composable
private fun ContentView() {
  Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
  ) {
    Text("Hello World!")
  }
}

@Preview
@Composable
private fun RemindersViewPreview() {
  RemindersView {
  }
}