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

package com.raywenderlich.learn.ui.latest

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.raywenderlich.learn.components.AddImagePreview
import com.raywenderlich.learn.ui.common.AddEmptyScreen
import com.raywenderlich.learn.ui.theme.colorAccent
import com.raywenderlich.learn.ui.theme.colorContent
import com.raywenderlich.learn.ui.theme.colorContent20Transparency
import com.raywenderlich.learn.ui.theme.colorContent85Transparency
import com.raywenderlich.learn.ui.theme.colorContentSecondary
import com.raywenderlich.learn.data.model.PLATFORM
import com.raywenderlich.learn.data.model.RWEntry
import com.raywenderlich.learn.platform.Logger

private const val TAG = "LatestContent"

@OptIn(ExperimentalPagerApi::class)
@Composable
fun LatestContent(
  items: SnapshotStateMap<PLATFORM, List<RWEntry>>,
  onOpenEntry: (String) -> Unit
) {

  if (items.keys.isEmpty()) {
    AddEmptyScreen("Loading…")
  } else {
    AddPages(
      items = items,
      onOpenEntry = onOpenEntry
    )
  }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AddPages(
  items: SnapshotStateMap<PLATFORM, List<RWEntry>>,
  onOpenEntry: (String) -> Unit
) {

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .background(color = colorContent)
  ) {

    item {
      Spacer(modifier = Modifier.height(16.dp))
    }

    item {

      val platforms = items.keys
      Logger.d(TAG, "platforms found=$platforms")

      for (platform in platforms) {
        AddNewPage(
          platform = platform,
          items = items[platform] ?: emptyList(),
          onOpenEntry = onOpenEntry
        )
      }
    }

    item {
      Spacer(modifier = Modifier.height(16.dp))
    }
  }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AddNewPage(
  platform: PLATFORM,
  items: List<RWEntry>,
  onOpenEntry: (String) -> Unit
) {
  val pagerState = rememberPagerState(
    pageCount = items.size,
    initialOffscreenLimit = 2,
  )

  Column(
    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
  ) {
    Text(
      text = platform.value.capitalize(Locale.current),
      style = MaterialTheme.typography.h4,
      color = colorAccent
    )

    HorizontalPager(
      state = pagerState,
      modifier = Modifier.fillMaxWidth(),
    ) { page ->

      AddNewPageEntry(
        entry = items[page],
        onOpenEntry = onOpenEntry
      )
    }

    if (pagerState.pageCount > 1) {
      HorizontalPagerIndicator(
        pagerState = pagerState,
        modifier = Modifier
          .align(Alignment.CenterHorizontally)
          .padding(16.dp),
        activeColor = colorAccent
      )
    }
  }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AddNewPageEntry(
  entry: RWEntry,
  onOpenEntry: (String) -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .aspectRatio(1.0f)
      .padding(top = 8.dp, bottom = 8.dp)
      .clickable {
        onOpenEntry(entry.link)
      },
    shape = RoundedCornerShape(16.dp),
    backgroundColor = colorContent,
    elevation = 0.dp
  ) {

    AddImagePreview(
      url = entry.imageUrl,
      modifier = Modifier
        .fillMaxSize()
        .clip(RoundedCornerShape(16.dp))
        .background(color = colorContentSecondary)
    )

    val verticalGradientBrush = Brush.verticalGradient(
      colors = listOf(
        colorContent20Transparency,
        colorContent85Transparency
      )
    )

    Column(
      modifier = Modifier
        .fillMaxSize()
        .background(brush = verticalGradientBrush),
      verticalArrangement = Arrangement.Bottom,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {

      Text(
        text = entry.title,
        modifier = Modifier.padding(16.dp),
        style = MaterialTheme.typography.h4,
        color = colorAccent
      )
    }
  }
}