/*
 * Copyright (c) 2022 Razeware LLC
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

package com.raywenderlich.learn.ui.bookmark

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.raywenderlich.learn.ui.common.AddEmptyScreen
import com.raywenderlich.learn.ui.common.AddEntryContent
import com.raywenderlich.learn.ui.theme.colorContent
import com.raywenderlich.learn.data.model.RWEntry
import com.raywenderlich.learn.logger.Logger
import kotlinx.coroutines.CoroutineScope

private const val TAG = "BookmarkContent"

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BookmarkContent(
  selected: MutableState<RWEntry>,
  items: State<List<RWEntry>?>,
  coroutineScope: CoroutineScope,
  bottomSheetScaffoldState: BottomSheetScaffoldState,
  onOpenEntry: (String) -> Unit
) {

  Logger.d(TAG, "Items received | items=${items.value?.size}")

  if (items.value == null || items.value.isNullOrEmpty()) {
    AddEmptyScreen(
      text = "You currently don't have any bookmark."
    )
  } else {
    AddBookmarks(
      selected = selected,
      items = items.value ?: emptyList(),
      coroutineScope = coroutineScope,
      bottomSheetScaffoldState = bottomSheetScaffoldState,
      onOpenEntry = onOpenEntry
    )
  }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddBookmarks(
  selected: MutableState<RWEntry>,
  items: List<RWEntry>,
  coroutineScope: CoroutineScope,
  bottomSheetScaffoldState: BottomSheetScaffoldState,
  onOpenEntry: (String) -> Unit
) {

  val size = items.size

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .background(color = colorContent)
  ) {

    itemsIndexed(items) { index, item ->
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