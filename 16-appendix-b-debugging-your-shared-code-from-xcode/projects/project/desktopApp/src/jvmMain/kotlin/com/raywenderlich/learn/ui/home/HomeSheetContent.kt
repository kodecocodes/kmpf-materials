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

package com.raywenderlich.learn.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.raywenderlich.learn.ui.theme.colorAccent
import com.raywenderlich.learn.data.model.RWEntry
import com.raywenderlich.learn.platform.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private const val TAG = "HomeSheetContent"

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeSheetContent(
  item: MutableState<RWEntry>,
  coroutineScope: CoroutineScope,
  bottomSheetScaffoldState: BottomSheetScaffoldState,
  onAddToBookmarks: (RWEntry) -> Unit,
) {

  Logger.d(TAG, "Selected item=${item.value}")

  Column(
    modifier = Modifier.fillMaxWidth()
  ) {

    Row(
      modifier = Modifier
        .fillMaxWidth()
        .clickable {
          coroutineScope.launch {
            bottomSheetScaffoldState.bottomSheetState.collapse()
          }

          onAddToBookmarks(item.value)
        }
    ) {

      val text = if (item.value.bookmarked) {
        "Remove from bookmarks"
      } else {
        "Add to bookmarks"
      }

      Text(
        text = text,
        style = MaterialTheme.typography.h4,
        color = colorAccent,
        modifier = Modifier
          .fillMaxWidth()
          .padding(start = 16.dp, top = 24.dp, end = 16.dp, bottom = 12.dp)
      )
    }
  }
}