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

package com.kodeco.learn.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.sp

private val fontSizeBig = 16.sp
private val fontSizeMedium = 15.sp
private val fontSizeSmall = 14.sp
private val fontSizeTiny = 12.sp

private val OpenSansFontFamily = FontFamily(
  Font("font/opensans_bold.ttf", FontWeight.Bold),
  Font("font/opensans_extrabold.ttf", FontWeight.ExtraBold),
  Font("font/opensans_light.ttf", FontWeight.Light),
  Font("font/opensans_regular.ttf", FontWeight.Normal),
  Font("font/opensans_semibold.ttf", FontWeight.SemiBold),
)

// Set of Material typography styles to start with
val Typography = Typography(
    headlineLarge = TextStyle(
        fontFamily = OpenSansFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = fontSizeMedium
    ),

    headlineMedium = TextStyle(
        fontFamily = OpenSansFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = fontSizeSmall
    ),

    headlineSmall = TextStyle(
        fontFamily = OpenSansFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = fontSizeTiny
    ),

    bodyLarge = TextStyle(
        fontFamily = OpenSansFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = fontSizeBig
    ),

    bodyMedium = TextStyle(
        fontFamily = OpenSansFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = fontSizeSmall
    ),

    bodySmall = TextStyle(
        fontFamily = OpenSansFontFamily,
        fontSize = fontSizeSmall
    )
)