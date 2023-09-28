/*
 * Copyright (c) 2023 Kodeco LLC
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

package com.yourcompany.organize.presentation

import com.yourcompany.organize.Platform
import kotlin.math.max
import kotlin.math.min

class AboutViewModel(
  platform: Platform
): BaseViewModel() {
  val items: List<RowItem> = makeRowItems(platform)

  private fun makeRowItems(platform: Platform): List<RowItem> {
    val rowItems = mutableListOf(
      RowItem("Operating System", "${platform.osName} ${platform.osVersion}"),
      RowItem("Device", platform.deviceModel),
      RowItem("CPU", platform.cpuType),
    )

    val max = max(platform.screen.width, platform.screen.height)
    val min = min(platform.screen.width, platform.screen.height)

    var displayInfo = "${max}×${min}"
    platform.screen.density?.let {
      displayInfo += " ${it}x"
    }

    rowItems.add(
      RowItem("Display", displayInfo)
    )

    return rowItems
  }

  data class RowItem(
    val title: String,
    val subtitle: String,
  )
}