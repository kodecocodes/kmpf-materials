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

package com.raywenderlich.learn.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.raywenderlich.learn.data.model.RWEntry
import com.raywenderlich.learn.platform.Logger
import com.raywenderlich.learn.ui.theme.colorAccent
import com.raywenderlich.learn.ui.theme.colorAccent25Transparency
import com.raywenderlich.learn.ui.utils.converterIso8601ToReadableDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private const val TAG = "EntryContent"

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddEntryContent(
  item: RWEntry,
  selected: MutableState<RWEntry>,
  divider: Boolean = true,
  coroutineScope: CoroutineScope,
  bottomSheetScaffoldState: BottomSheetScaffoldState,
  onOpenEntry: (String) -> Unit
) {

  Logger.d(TAG, "item | title=${item.title} | updated=${item.updated}")


  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
      .clickable {
        onOpenEntry(item.link)
      }
  ) {

    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.Start
    ) {

      Row(
        modifier = Modifier
          .fillMaxWidth()
          .weight(1.0f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
      ) {

        val icon = painterResource("images/ic_launcher.png")
        val description = "Feed icon"

        Image(
          painter = icon,
          contentDescription = description,
          modifier = Modifier.size(40.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(
          modifier = Modifier.fillMaxWidth()
        ) {

          Text(
            text = "Ray Wenderlich",
            style = MaterialTheme.typography.h1,
            color = colorAccent
          )

          Text(
            text = converterIso8601ToReadableDate(item.updated),
            style = MaterialTheme.typography.h3,
            color = colorAccent
          )
        }
      }

      Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.End
      ) {

        val icon = painterResource("images/ic_more.png")
        val description = "More actions"

        Image(
          painter = icon,
          contentDescription = description,
          modifier = Modifier
            .size(25.dp)
            .clickable {
              selected.value = item
              coroutineScope.launch {
                bottomSheetScaffoldState.bottomSheetState.expand()
              }
            },
          colorFilter = ColorFilter.tint(colorAccent)
        )
      }
    }

    Spacer(modifier = Modifier.height(8.dp))

    Text(
      text = item.title,
      style = MaterialTheme.typography.h1,
      color = colorAccent
    )

    Spacer(modifier = Modifier.height(4.dp))

    Text(
      text = item.summary,
      style = MaterialTheme.typography.h2,
      color = colorAccent,
      maxLines = 2,
      overflow = TextOverflow.Ellipsis
    )

    Spacer(modifier = Modifier.height(16.dp))

    if (divider) {
      Divider(
        modifier = Modifier.fillMaxWidth(),
        color = colorAccent25Transparency,
        thickness = 1.dp
      )
    }
  }
}