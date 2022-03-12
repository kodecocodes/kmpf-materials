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

package com.raywenderlich.learn.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.raywenderlich.learn.R
import com.raywenderlich.learn.logger.Logger
import com.raywenderlich.learn.ui.theme.colorAccent
import com.raywenderlich.learn.ui.theme.colorContent

private const val TAG = "ImagePreview"

@OptIn(ExperimentalCoilApi::class)
@Composable
fun AddImagePreview(
  url: String,
  modifier: Modifier
) {

  Logger.d(TAG, "Loading image from uri=$url")

  if (url.isEmpty()) {
    Logger.d(TAG, "Empty url")
    AddImagePreviewEmpty(modifier)

  } else {

    val request = ImageRequest.Builder(LocalContext.current)
      .data(url)
      .crossfade(true)
      .build()

    val painter = rememberImagePainter(
      request = request
    )

    Box {

      Image(
        painter = painter,
        contentScale = ContentScale.Crop,
        contentDescription = stringResource(id = R.string.description_preview),
        modifier = modifier
      )

      when (painter.state) {
        is ImagePainter.State.Loading -> {
          AddImagePreviewEmpty(modifier)
        }
        is ImagePainter.State.Error -> {
          AddImagePreviewEmpty(modifier)
        }
        else -> {
          // Do nothing
        }
      }
    }
  }
}

@Composable
fun AddImagePreviewEmpty(
  modifier: Modifier
) {

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(colorAccent),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {

    Surface(
      modifier = modifier,
      color = Color.Transparent
    ) {

      val icon = painterResource(R.drawable.ic_brand)
      val description = stringResource(id = R.string.description_preview_error)

      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
      ) {

        Image(
          painter = icon,
          contentScale = ContentScale.Crop,
          contentDescription = description,
          modifier = modifier,
          colorFilter = ColorFilter.tint(color = colorContent)
        )
      }
    }
  }
}