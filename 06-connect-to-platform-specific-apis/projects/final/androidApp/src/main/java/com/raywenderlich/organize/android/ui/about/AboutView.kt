package com.raywenderlich.organize.android.ui.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raywenderlich.organize.Platform
import kotlin.math.max
import kotlin.math.min

@Composable
fun AboutView(
  onUpButtonClick: () -> Unit
) {
  Column {
    Toolbar(onUpButtonClick = onUpButtonClick)
    ContentView()
  }
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

@Composable
private fun ContentView() {
  val items = makeItems()

  LazyColumn(
    modifier = Modifier.fillMaxSize(),
  ) {
    items(items) { row ->
      RowView(title = row.first, subtitle = row.second)
    }
  }
}

@Composable
private fun RowView(
  title: String,
  subtitle: String,
) {
  Column(modifier = Modifier.fillMaxWidth()) {
    Column(Modifier.padding(8.dp)) {
      Text(
        text = title,
        style = MaterialTheme.typography.caption,
        color = Color.Gray,
      )
      Text(
        text = subtitle,
        style = MaterialTheme.typography.body1,
      )
    }
    Divider()
  }
}

private fun makeItems(): List<Pair<String, String>> {
  val platform = Platform()

  val items = mutableListOf(
    Pair("Operating System", "${platform.osName} ${platform.osVersion}"),
    Pair("Device", platform.deviceModel),
    Pair("CPU", platform.cpuType)
  )

  platform.screen?.let {
    val max = max(it.width, it.height)
    val min = min(it.width, it.height)

    items.add(Pair("Display", "${max}×${min} @${it.density}x"))
  }

  return items
}

@Preview(showBackground = true)
@Composable
private fun RowViewPreview() {
  AboutView {
  }
}
