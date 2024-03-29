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

package com.kodeco.learn.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.kodeco.learn.components.AddImagePreview
import com.kodeco.learn.data.model.KodecoEntry
import com.kodeco.learn.logger.Logger
import com.kodeco.learn.ui.MR
import com.kodeco.learn.utils.converterIso8601ToReadableDate
import dev.icerock.moko.resources.compose.fontFamilyResource
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private const val TAG = "EntryContent"

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddEntryContent(
  item: KodecoEntry,
  selected: MutableState<KodecoEntry>,
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

        AddImagePreview(
          url = item.imageUrl,
          modifier = Modifier
            .size(40.dp)
            .clip(RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(
          modifier = Modifier.fillMaxWidth()
        ) {

          Text(
            text = stringResource(MR.strings.app_kodeco),
            fontFamily = fontFamilyResource(MR.fonts.OpenSans.regular)
          )

          Text(
            text = converterIso8601ToReadableDate(item.updated),
            fontFamily = fontFamilyResource(MR.fonts.OpenSans.regular)
          )
        }
      }

      Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.End
      ) {

        val resource = painterResource(MR.images.ic_more)
        val description = stringResource(MR.strings.description_more)

        Icon(
          painter = resource,
          contentDescription = description,
          modifier = Modifier
            .size(25.dp)
            .clickable {
              selected.value = item
              coroutineScope.launch {
                bottomSheetScaffoldState.bottomSheetState.expand()
              }
            }
        )
      }
    }

    Spacer(modifier = Modifier.height(8.dp))

    Text(
      text = item.title,
      fontFamily = fontFamilyResource(MR.fonts.OpenSans.regular)
    )

    Spacer(modifier = Modifier.height(4.dp))

    Text(
      text = item.summary,
      maxLines = 2,
      overflow = TextOverflow.Ellipsis,
      fontFamily = fontFamilyResource(MR.fonts.OpenSans.regular)
    )

    Spacer(modifier = Modifier.height(16.dp))

    if (divider) {
      Divider(
        modifier = Modifier.fillMaxWidth(),
        thickness = 1.dp
      )
    }
  }
}