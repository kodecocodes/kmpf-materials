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

package com.kodeco.learn.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.kodeco.learn.ServiceLocator
import com.kodeco.learn.components.AddImagePreview
import com.kodeco.learn.data.model.KodecoEntry
import com.kodeco.learn.data.model.PLATFORM
import com.kodeco.learn.platform.Logger
import com.kodeco.learn.ui.common.AddEntryContent
import com.kodeco.learn.ui.theme.Fonts
import kotlinx.coroutines.CoroutineScope

private const val TAG = "HomeContent"

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeContent(
  selected: MutableState<KodecoEntry>,
  items: SnapshotStateMap<PLATFORM, List<KodecoEntry>>,
  coroutineScope: CoroutineScope,
  bottomSheetScaffoldState: BottomSheetScaffoldState,
  onOpenEntry: (String) -> Unit
) {

  val size = items.size
  val platform = remember { mutableStateOf(PLATFORM.ALL) }

  Logger.d(TAG, "Items received | items=${items.size}")

  LazyColumn(
    modifier = Modifier.fillMaxSize()
  ) {

    item {
      Spacer(modifier = Modifier.height(16.dp))
    }

    item {
      AddPlatformHeadings(
        platform = platform
      )
    }

    item {
      Spacer(modifier = Modifier.height(16.dp))
    }

    if (!items.isEmpty()) {
      val feed = items[platform.value] ?: emptyList()
      itemsIndexed(feed) { index, item ->
        AddEntryContent(
          item = item,
          selected = selected,
          divider = index < size - 1,
          coroutineScope = coroutineScope,
          bottomSheetScaffoldState = bottomSheetScaffoldState,
          onOpenEntry = onOpenEntry
        )
      }
    }
  }
}

@Composable
fun AddPlatformHeadings(
  platform: MutableState<PLATFORM>
) {

  LazyRow(
    modifier = Modifier.fillMaxWidth()
  ) {

    items(ServiceLocator.getFeedPresenter.content) {

      Spacer(modifier = Modifier.width(16.dp))

      Column(
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        AddImagePreview(
          url = it.image,
          modifier = Modifier
            .size(125.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable {
              platform.value = it.platform
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
          text = it.platform.value,
          fontFamily = Fonts.OpenSansFontFamily()
        )
      }
    }

    item {
      Spacer(modifier = Modifier.width(16.dp))
    }
  }
}