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

package com.kodeco.learn.ui.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kodeco.learn.data.model.KodecoEntry
import com.kodeco.learn.data.model.PLATFORM
import com.kodeco.learn.logger.Logger
import com.kodeco.learn.ui.common.AddEntryContent
import kotlinx.coroutines.CoroutineScope

private const val TAG = "SearchContent"

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchContent(
    selected: MutableState<KodecoEntry>,
    items: SnapshotStateMap<PLATFORM, List<KodecoEntry>>,
    coroutineScope: CoroutineScope,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    onOpenEntry: (String) -> Unit
) {

  Surface {

    val search = remember { mutableStateOf("") }

    val keys = items.keys

    val results = mutableListOf<KodecoEntry>()
    for (key in keys) {
      if (key == PLATFORM.ALL) {
        continue
      }

      Logger.d(TAG, "key=$key")

      val list = items[key]!!.toMutableList()

      Logger.d(TAG, "list=$list")
      results += list.filter {
        (it.title.lowercase().contains(search.value.lowercase())) ||
            (it.summary.lowercase().contains(search.value.lowercase()))
      }
    }

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),

        content = {

          item {
            AddSearchField(search)
          }

          items(results) {

            AddEntryContent(
                item = it,
                selected = selected,
                coroutineScope = coroutineScope,
                bottomSheetScaffoldState = bottomSheetScaffoldState,
                onOpenEntry = onOpenEntry
            )
          }
        }
    )
  }
}

@Composable
fun AddSearchField(search: MutableState<String>) {

  val focused = remember { mutableStateOf(false) }

  OutlinedTextField(
      value = search.value,
      onValueChange = { value ->
        search.value = value
      },
      modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp)
          .onFocusChanged {
            focused.value = it.isFocused
          },
      placeholder = {
        Text(
            text = "Search"
        )
      },
      leadingIcon = {
        val resource = painterResource("images/ic_search.xml")
        val description = "Search for a specific article"

        Icon(
            painter = resource,
            contentDescription = description
        )
      }
  )
}