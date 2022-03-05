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

package com.raywenderlich.learn.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Surface
import androidx.compose.material.contentColorFor
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private val AppBarHeight = 56.dp

/**
 * Mimics [TopAppBar] with a few changes that aren't possible to override
 */

@Composable
fun RWTopAppBar(
  title: @Composable () -> Unit,
  modifier: Modifier = Modifier,
  actions: @Composable RowScope.() -> Unit = {},
  backgroundColor: Color = MaterialTheme.colors.primarySurface,
  contentColor: Color = contentColorFor(backgroundColor),
  elevation: Dp = AppBarDefaults.TopAppBarElevation
) {
  AppBar(
    backgroundColor,
    contentColor,
    elevation,
    AppBarDefaults.ContentPadding,
    RectangleShape,
    modifier
  ) {
    Row(
      Modifier.fillMaxSize(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.Center
    ) {
      ProvideTextStyle(value = MaterialTheme.typography.h6) {
        CompositionLocalProvider(
          LocalContentAlpha provides ContentAlpha.high,
          content = title
        )
      }
    }

    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
      Row(
        Modifier.fillMaxHeight(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
        content = actions
      )
    }
  }
}

@Composable
private fun AppBar(
  backgroundColor: Color,
  contentColor: Color,
  elevation: Dp,
  contentPadding: PaddingValues,
  shape: Shape,
  modifier: Modifier = Modifier,
  content: @Composable RowScope.() -> Unit
) {
  Surface(
    color = backgroundColor,
    contentColor = contentColor,
    elevation = elevation,
    shape = shape,
    modifier = modifier
  ) {
    Row(
      Modifier
        .fillMaxWidth()
        .padding(contentPadding)
        .height(AppBarHeight),
      horizontalArrangement = Arrangement.Start,
      verticalAlignment = Alignment.CenterVertically,
      content = content
    )
  }
}

