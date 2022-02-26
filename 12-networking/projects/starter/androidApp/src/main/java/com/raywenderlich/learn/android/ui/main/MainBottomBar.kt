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

package com.raywenderlich.learn.android.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.raywenderlich.learn.android.ui.theme.colorAccent
import com.raywenderlich.learn.android.ui.theme.colorAccent25Transparency
import com.raywenderlich.learn.android.ui.theme.colorContent
import com.raywenderlich.learn.android.ui.theme.colorPrimary
import com.raywenderlich.learn.android.ui.theme.colorSecondary

private lateinit var selectedIndex: MutableState<Int>

@Composable
fun MainBottomBar(
  navController: NavHostController,
  items: List<BottomNavigationScreens>
) {

  Column {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .height(1.dp)
        .background(color = colorAccent25Transparency)
    ) {}

    AppBottomNavigation(
      navController = navController,
      items = items
    )
  }
}

@Composable
private fun AppBottomNavigation(
  navController: NavHostController,
  items: List<BottomNavigationScreens>
) {
  BottomNavigation(
    backgroundColor = colorPrimary,
    contentColor = colorSecondary
  ) {

    selectedIndex = remember { mutableStateOf(0) }

    items.forEachIndexed { index, screen ->

      val isSelected = selectedIndex.value == index

      val style = if (isSelected) {
        typography.subtitle1.copy(color = colorPrimary)
      } else {
        typography.subtitle2.copy(color = colorAccent)
      }

      BottomNavigationItem(
        modifier = Modifier
          .background(color = colorContent),
        icon = {
          Icon(
            painter = painterResource(id = screen.drawResId),
            contentDescription = stringResource(id = screen.stringResId)
          )
        },
        label = {
          Text(
            stringResource(id = screen.stringResId),
            style = style
          )
        },
        selected = isSelected,
        selectedContentColor = colorPrimary,
        unselectedContentColor = colorAccent,
        alwaysShowLabel = true,
        onClick = {
          if (!isSelected) {
            selectedIndex.value = index
            navController.navigate(screen.route) {
              popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
              }

              launchSingleTop = true
              restoreState = true
            }
          }
        }
      )
    }
  }
}