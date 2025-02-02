/*
 * Copyright (c) 2025 Kodeco Inc
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.kodeco.learn.resources.Res
import com.kodeco.learn.resources.opensans_bold
import com.kodeco.learn.resources.opensans_extrabold
import com.kodeco.learn.resources.opensans_light
import com.kodeco.learn.resources.opensans_regular
import com.kodeco.learn.resources.opensans_semibold
import org.jetbrains.compose.resources.Font

private val fontSizeBig = 16.sp
private val fontSizeMedium = 15.sp
private val fontSizeSmall = 14.sp
private val fontSizeTiny = 12.sp

@Composable
fun OpenSansFontFamily() = FontFamily(
  Font(Res.font.opensans_bold, FontWeight.Bold, FontStyle.Normal),
  Font(Res.font.opensans_extrabold, FontWeight.ExtraBold, FontStyle.Normal),
  Font(Res.font.opensans_light, FontWeight.Light, FontStyle.Normal),
  Font(Res.font.opensans_regular, FontWeight.Normal, FontStyle.Normal),
  Font(Res.font.opensans_semibold, FontWeight.SemiBold, FontStyle.Normal)
)

// Set of Material typography styles to start with
fun Typography(family: FontFamily) = Typography(
    headlineLarge = TextStyle(
        fontFamily = family,
        fontWeight = FontWeight.Bold,
        fontSize = fontSizeMedium
    ),

    headlineMedium = TextStyle(
        fontFamily = family,
        fontWeight = FontWeight.Normal,
        fontSize = fontSizeSmall
    ),

    headlineSmall = TextStyle(
        fontFamily = family,
        fontWeight = FontWeight.Bold,
        fontSize = fontSizeTiny
    ),

    bodyLarge = TextStyle(
        fontFamily = family,
        fontWeight = FontWeight.Normal,
        fontSize = fontSizeBig
    ),

    bodyMedium = TextStyle(
        fontFamily = family,
        fontWeight = FontWeight.Bold,
        fontSize = fontSizeSmall
    ),

    bodySmall = TextStyle(
        fontFamily = family,
        fontSize = fontSizeSmall
    ),

    titleLarge = TextStyle(
        fontFamily = family,
        fontWeight = FontWeight.Normal,
        fontSize = fontSizeBig
    ),

    titleMedium = TextStyle(
        fontFamily = family,
        fontWeight = FontWeight.Bold,
        fontSize = fontSizeSmall
    ),

    titleSmall = TextStyle(
        fontFamily = family,
        fontSize = fontSizeSmall
    ),

    labelLarge = TextStyle(
        fontFamily = family,
        fontWeight = FontWeight.Normal,
        fontSize = fontSizeBig
    ),

    labelMedium = TextStyle(
        fontFamily = family,
        fontWeight = FontWeight.Bold,
        fontSize = fontSizeSmall
    ),

    labelSmall = TextStyle(
        fontFamily = family,
        fontSize = fontSizeSmall
    )
)