package ui.about

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.raywenderlich.organize.Platform
import kotlin.math.max
import kotlin.math.min

@Composable
fun AboutView() {
  ContentView()
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

@Composable
private fun RowView(
  title: String,
  subtitle: String,
) {
  Column(modifier = Modifier.fillMaxWidth()) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.padding(8.dp)
    ) {
      Text(
        text = title,
        style = MaterialTheme.typography.caption,
        color = Color.Gray,
        modifier = Modifier.weight(1f)
      )
      Text(
        text = subtitle,
        style = MaterialTheme.typography.body1,
        modifier = Modifier.padding(8.dp)
      )
    }
    Divider()
  }
}

@Preview
@Composable
private fun RowViewPreview() {
  LazyColumn {
    items(5) {
      RowView(
        title = "Title",
        subtitle = "Subtitle",
      )
    }
  }
}
