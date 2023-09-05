/*
 * Copyright (c) 2023 Kodeco Inc
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

package com.kodeco.learn.ui.latest

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kodeco.learn.R
import com.kodeco.learn.components.AddImagePreview
import com.kodeco.learn.components.HorizontalPagerIndicator
import com.kodeco.learn.data.model.KodecoEntry
import com.kodeco.learn.data.model.PLATFORM
import com.kodeco.learn.platform.Logger
import com.kodeco.learn.ui.common.AddEmptyScreen
import com.kodeco.learn.ui.theme.colorContent20Transparency
import com.kodeco.learn.ui.theme.colorContent85Transparency

private const val TAG = "LatestContent"

@Composable
fun LatestContent(
  items: SnapshotStateMap<PLATFORM, List<KodecoEntry>>,
  onOpenEntry: (String) -> Unit
) {

  if (items.keys.isEmpty()) {
    AddEmptyScreen(stringResource(R.string.empty_screen_loading))
  } else {
    AddPages(
      items = items,
      onOpenEntry = onOpenEntry
    )
  }
}

@Composable
fun AddPages(
  items: SnapshotStateMap<PLATFORM, List<KodecoEntry>>,
  onOpenEntry: (String) -> Unit
) {

  LazyColumn(
    modifier = Modifier.fillMaxSize()
  ) {

    item {
      Spacer(modifier = Modifier.height(16.dp))
    }

    items(items.keys.toMutableList().sortedBy { it.name }) { platform ->

      Logger.d(TAG, "Adding platform=$platform")

      AddNewPage(
        platform = platform,
        items = items[platform] ?: emptyList(),
        onOpenEntry = onOpenEntry
      )
    }

    item {
      Spacer(modifier = Modifier.height(16.dp))
    }
  }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddNewPage(
  platform: PLATFORM,
  items: List<KodecoEntry>,
  onOpenEntry: (String) -> Unit
) {
  val pagerState = rememberPagerState(pageCount = {
    items.size
  })

  Column(
    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
  ) {
    Text(
      text = platform.value
    )

    HorizontalPager(
      state = pagerState
    ) { page ->

      AddNewPageEntry(
        entry = items[page],
        onOpenEntry = onOpenEntry
      )
    }

    if (items.size > 1) {
      HorizontalPagerIndicator(
        pagerState = pagerState,
        pageCount = items.size,
        modifier = Modifier
          .align(Alignment.CenterHorizontally)
          .padding(16.dp),
        activeColor = MaterialTheme.colorScheme.secondaryContainer
      )
    }
  }
}

@Composable
fun AddNewPageEntry(
  entry: KodecoEntry,
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
    shape = RoundedCornerShape(16.dp)
  ) {

    AddImagePreview(
      url = entry.imageUrl,
      modifier = Modifier
        .fillMaxSize()
        .clip(RoundedCornerShape(16.dp))
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
        modifier = Modifier.padding(16.dp)
      )
    }
  }
}