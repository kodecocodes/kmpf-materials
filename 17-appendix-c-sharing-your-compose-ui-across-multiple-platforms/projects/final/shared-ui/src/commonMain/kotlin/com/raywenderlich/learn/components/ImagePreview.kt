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
import com.raywenderlich.learn.MR
import com.raywenderlich.learn.platform.Logger
import com.raywenderlich.learn.ui.theme.colorAccent
import com.raywenderlich.learn.ui.theme.colorContent
import com.raywenderlich.learn.ui.theme.icBrand
import com.raywenderlich.learn.ui.utils.getString
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import io.kamel.image.lazyPainterResource

private const val TAG = "ImagePreview"

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
    Box {
      when (val resource = lazyPainterResource(url)) {
        is Resource.Loading -> {
          Logger.d(TAG, "Loading image from uri=$url")
          AddImagePreviewEmpty(modifier)
        }
        is Resource.Success -> {
          Logger.d(TAG, "Loading successful image from uri=$url")

          KamelImage(
            resource = resource,
            contentScale = ContentScale.Crop,
            contentDescription = "Image preview",
            modifier = modifier,
            crossfade = true
          )
        }
        is Resource.Failure -> {
          Logger.d(TAG, "Loading failed image from uri=$url. Reason=${resource.exception}")

          AddImagePreviewEmpty(modifier)
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

      val icon = icBrand()
      val description = getString(MR.strings.description_preview_error)

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