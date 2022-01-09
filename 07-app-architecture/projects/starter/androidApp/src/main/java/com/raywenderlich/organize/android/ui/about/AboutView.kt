/*
 * Copyright (c) 2021 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

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
private fun AboutPreview() {
  AboutView {
  }
}
