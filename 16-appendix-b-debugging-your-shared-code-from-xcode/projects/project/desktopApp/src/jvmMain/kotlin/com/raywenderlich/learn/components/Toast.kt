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

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raywenderlich.learn.ui.theme.colorAccent
import com.raywenderlich.learn.ui.theme.colorContent
import com.raywenderlich.learn.ui.theme.colorContentSecondary
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Toast.kt
 *
 * Toast message class for Desktop applications. Original from JetBrains compose-jb samples.
 *
 * Source: https://github.com/JetBrains/compose-jb
 */

enum class ToastDuration(val value: Int) {
  Short(1000), Long(3000)
}

private var isShown: Boolean = false

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun Toast(
  text: String,
  visibility: MutableState<Boolean> = mutableStateOf(false),
  duration: ToastDuration = ToastDuration.Long
) {
  if (isShown) {
    return
  }

  if (visibility.value) {
    isShown = true
    Box(
      modifier = Modifier.fillMaxSize().padding(bottom = 70.dp),
      contentAlignment = Alignment.BottomCenter
    ) {
      Surface(
        modifier = Modifier
          .size(300.dp, 70.dp)
          .border(2.dp, colorContentSecondary, RoundedCornerShape(4.dp)),
        color = colorContent,
        shape = RoundedCornerShape(4.dp)
      ) {
        Box(contentAlignment = Alignment.Center) {
          Text(
            text = text,
            fontSize = 17.sp,
            color = colorAccent
          )
        }
        DisposableEffect(Unit) {
          GlobalScope.launch {
            delay(duration.value.toLong())
            isShown = false
            visibility.value = false
          }
          onDispose {  }
        }
      }
    }
  }
}
