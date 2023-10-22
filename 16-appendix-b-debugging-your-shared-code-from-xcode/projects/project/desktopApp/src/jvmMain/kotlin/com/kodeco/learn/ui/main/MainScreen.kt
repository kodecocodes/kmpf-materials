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

package com.kodeco.learn.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kodeco.learn.data.model.GravatarEntry
import com.kodeco.learn.data.model.KodecoEntry
import com.kodeco.learn.data.model.PLATFORM
import com.kodeco.learn.ui.home.HomeSheetContent
import moe.tlaster.precompose.navigation.rememberNavigator

private lateinit var selected: MutableState<KodecoEntry>

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    profile: GravatarEntry?,
    feeds: SnapshotStateMap<PLATFORM, List<KodecoEntry>>,
    bookmarks: State<List<KodecoEntry>?>,
    onUpdateBookmark: (KodecoEntry) -> Unit,
    onShareAsLink: (KodecoEntry) -> Unit,
    onOpenEntry: (String) -> Unit
) {

  val bottomNavigationItems = listOf(
      BottomNavigationScreens.Home,
      BottomNavigationScreens.Bookmark,
      BottomNavigationScreens.Latest,
      BottomNavigationScreens.Search
  )

  val navController = rememberNavigator()

  val coroutineScope = rememberCoroutineScope()
  val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()

  selected = remember { mutableStateOf(KodecoEntry()) }

  BottomSheetScaffold(
      sheetContent = {
        HomeSheetContent(
            coroutineScope = coroutineScope,
            bottomSheetScaffoldState = bottomSheetScaffoldState,
            item = selected,
            onUpdateBookmark = onUpdateBookmark,
            onShareAsLink = onShareAsLink
        )
      },
      sheetShape = RoundedCornerShape(
          topStart = 16.dp,
          topEnd = 16.dp
      ),
      scaffoldState = bottomSheetScaffoldState, sheetPeekHeight = 0.dp
  ) {

    Scaffold(
        topBar = {
          MainTopAppBar(
              profile = profile
          )
        },
        bottomBar = {
          MainBottomBar(
              navController = navController,
              items = bottomNavigationItems
          )
        },
        content = {
          Column(
              modifier = Modifier.padding(it)
          ) {
            MainContent(
                navController = navController,
                coroutineScope = coroutineScope,
                bottomSheetScaffoldState = bottomSheetScaffoldState,
                selected = selected,
                feeds = feeds,
                bookmarks = bookmarks,
                onOpenEntry = onOpenEntry
            )
          }
        }
    )
  }
}