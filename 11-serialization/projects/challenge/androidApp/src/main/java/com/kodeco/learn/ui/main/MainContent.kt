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
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kodeco.learn.data.model.KodecoEntry
import com.kodeco.learn.data.model.PLATFORM
import com.kodeco.learn.ui.bookmark.BookmarkContent
import com.kodeco.learn.ui.home.HomeContent
import com.kodeco.learn.ui.latest.LatestContent
import com.kodeco.learn.ui.search.SearchContent
import kotlinx.coroutines.CoroutineScope

private val DEFAULT_SCREEN = BottomNavigationScreens.Home

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainContent(
    navController: NavHostController,
    coroutineScope: CoroutineScope,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    selected: MutableState<KodecoEntry>,
    feeds: SnapshotStateMap<PLATFORM, List<KodecoEntry>>,
    bookmarks: State<List<KodecoEntry>?>,
    onOpenEntry: (String) -> Unit
) {

  Column {
    MainScreenNavigationConfigurations(
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun MainScreenNavigationConfigurations(
    navController: NavHostController,
    coroutineScope: CoroutineScope,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    selected: MutableState<KodecoEntry>,
    feeds: SnapshotStateMap<PLATFORM, List<KodecoEntry>>,
    bookmarks: State<List<KodecoEntry>?>,
    onOpenEntry: (String) -> Unit
) {

  NavHost(
      navController,
      startDestination = DEFAULT_SCREEN.route
  ) {
    composable(BottomNavigationScreens.Home.route) {
      HomeContent(
          selected = selected,
          items = feeds,
          coroutineScope = coroutineScope,
          bottomSheetScaffoldState = bottomSheetScaffoldState,
          onOpenEntry = onOpenEntry
      )
    }
    composable(BottomNavigationScreens.Bookmark.route) {
      BookmarkContent(
          selected = selected,
          items = bookmarks,
          coroutineScope = coroutineScope,
          bottomSheetScaffoldState = bottomSheetScaffoldState,
          onOpenEntry = onOpenEntry
      )
    }
    composable(BottomNavigationScreens.Latest.route) {
      LatestContent(
          items = feeds,
          onOpenEntry = onOpenEntry
      )
    }
    composable(BottomNavigationScreens.Search.route) {
      SearchContent(
          selected = selected,
          items = feeds,
          coroutineScope = coroutineScope,
          bottomSheetScaffoldState = bottomSheetScaffoldState,
          onOpenEntry = onOpenEntry
      )
    }
  }
}