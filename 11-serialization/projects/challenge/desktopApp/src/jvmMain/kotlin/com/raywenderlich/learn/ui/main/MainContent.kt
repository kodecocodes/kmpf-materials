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

package com.raywenderlich.learn.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Modifier
import com.raywenderlich.learn.data.model.PLATFORM
import com.raywenderlich.learn.data.model.RWEntry
import com.raywenderlich.learn.ui.bookmark.BookmarkContent
import com.raywenderlich.learn.ui.home.HomeContent
import com.raywenderlich.learn.ui.latest.LatestContent
import com.raywenderlich.learn.ui.search.SearchContent
import com.raywenderlich.learn.ui.theme.BottomNavigationHeight
import com.raywenderlich.learn.ui.theme.colorContent
import kotlinx.coroutines.CoroutineScope
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.route.scene

private val DEFAULT_SCREEN = BottomNavigationScreens.Home

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainContent(
    navController: Navigator,
    coroutineScope: CoroutineScope,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    selected: MutableState<RWEntry>,
    feeds: SnapshotStateMap<PLATFORM, List<RWEntry>>,
    bookmarks: State<List<RWEntry>?>,
    onOpenEntry: (String) -> Unit
) {

    Column(
        modifier = Modifier
            .padding(bottom = BottomNavigationHeight)
            .background(colorContent)

    ) {
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
    navController: Navigator,
    coroutineScope: CoroutineScope,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    selected: MutableState<RWEntry>,
    feeds: SnapshotStateMap<PLATFORM, List<RWEntry>>,
    bookmarks: State<List<RWEntry>?>,
    onOpenEntry: (String) -> Unit
) {

    NavHost(navController, DEFAULT_SCREEN.route) {
        scene(BottomNavigationScreens.Home.route) {
            HomeContent(
                selected = selected,
                items = feeds,
                coroutineScope = coroutineScope,
                bottomSheetScaffoldState = bottomSheetScaffoldState,
                onOpenEntry = onOpenEntry
            )
        }
        scene(BottomNavigationScreens.Bookmark.route) {
            BookmarkContent(
                selected = selected,
                items = bookmarks,
                coroutineScope = coroutineScope,
                bottomSheetScaffoldState = bottomSheetScaffoldState,
                onOpenEntry = onOpenEntry
            )
        }
        scene(BottomNavigationScreens.Latest.route) {
            LatestContent(
                items = feeds,
                onOpenEntry = onOpenEntry
            )
        }
        scene(BottomNavigationScreens.Search.route) {
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