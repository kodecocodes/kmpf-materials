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

package com.raywenderlich.learn.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.raywenderlich.learn.R

private val fontSizeLarge = 23.sp
private val fontSizeMedium = 19.sp
private val fontSizeMediumSmall = 17.sp
private val fontSizeBigSmall = 16.sp
private val fontSizeSmall = 15.sp
private val fontSizeExtraSmall = 14.sp
private val fontSizeTiny = 12.sp

private val BitterFontFamily = FontFamily(
    Font(R.font.bitter_bold, FontWeight.Bold),
    Font(R.font.bitter_extrabold, FontWeight.ExtraBold),
    Font(R.font.bitter_light, FontWeight.Light),
    Font(R.font.bitter_regular),
    Font(R.font.bitter_semibold, FontWeight.SemiBold),
)

// Set of Material typography styles to start with
val Typography = Typography(
    h1 = TextStyle(
        color = colorAccent,
        fontFamily = BitterFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = fontSizeSmall
    ),

    h2 = TextStyle(
        color = colorAccent,
        fontFamily = BitterFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = fontSizeExtraSmall
    ),

    h3 = TextStyle(
        color = colorAccent,
        fontFamily = BitterFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = fontSizeTiny
    ),

    h4 = TextStyle(
        color = colorAccent,
        fontFamily = BitterFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = fontSizeMedium
    ),

    body1 = TextStyle(
        color = colorAccent,
        fontFamily = BitterFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = fontSizeBigSmall
    ),

    body2 = TextStyle(
        color = colorAccent,
        fontFamily = BitterFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = fontSizeExtraSmall
    ),

    subtitle1 = TextStyle(
        color = colorPrimary,
        fontFamily = BitterFontFamily,
        fontSize = fontSizeExtraSmall
    ),

    subtitle2 = TextStyle(
        color = colorPrimary,
        fontFamily = BitterFontFamily,
        fontSize = fontSizeExtraSmall
    )
)