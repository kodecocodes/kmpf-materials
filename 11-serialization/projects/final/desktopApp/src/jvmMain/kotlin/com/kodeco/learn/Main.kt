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

package com.kodeco.learn

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.kodeco.learn.data.model.KodecoEntry
import com.kodeco.learn.platform.Logger
import com.kodeco.learn.ui.bookmark.BookmarkViewModel
import com.kodeco.learn.ui.home.FeedViewModel
import com.kodeco.learn.ui.main.MainScreen
import com.kodeco.learn.ui.theme.KodecoTheme
import moe.tlaster.precompose.PreComposeWindow
import moe.tlaster.precompose.viewmodel.viewModel
import java.awt.Desktop
import java.net.URI

private lateinit var bookmarkViewModel: BookmarkViewModel
private lateinit var feedViewModel: FeedViewModel

private const val TAG = "main"

fun main() {

  application {
    val windowState = rememberWindowState(width = 460.dp, height = 900.dp)

    PreComposeWindow(
      onCloseRequest = ::exitApplication,
      state = windowState,
      title = "learn"
    ) {
      bookmarkViewModel = viewModel {
        BookmarkViewModel()
      }

      feedViewModel = viewModel {
        FeedViewModel()
      }

      feedViewModel.fetchAllFeeds()
      feedViewModel.fetchMyGravatar()
      bookmarkViewModel.getBookmarks()

      val items = feedViewModel.items
      val profile = feedViewModel.profile
      val bookmarks = bookmarkViewModel.items

      Surface(modifier = Modifier.fillMaxSize()) {

        KodecoTheme {
          MainScreen(
            profile = profile.value,
            feeds = items,
            bookmarks = bookmarks,
            onUpdateBookmark = { updateBookmark(it) },
            onShareAsLink = { shareAsLink(it) },
            onOpenEntry = { openEntry(it) }
          )
        }
      }
    }
  }
}

private fun updateBookmark(item: KodecoEntry) {
  if (item.bookmarked) {
    removedFromBookmarks(item)
  } else {
    addToBookmarks(item)
  }
}

private fun addToBookmarks(item: KodecoEntry) {
  bookmarkViewModel.addAsBookmark(item)
  bookmarkViewModel.getBookmarks()
}

private fun removedFromBookmarks(item: KodecoEntry) {
  bookmarkViewModel.removeFromBookmark(item)
  bookmarkViewModel.getBookmarks()
}

private fun shareAsLink(item: KodecoEntry) {
  try {
    val desktop = Desktop.getDesktop()
    desktop.browse(URI.create(item.link))
  } catch(e: Exception) {
    Logger.d(TAG, "Unable to open url. Reason: ${e.stackTrace}")
  }
}

private fun openEntry(url: String) {
  try {
    val desktop = Desktop.getDesktop()
    desktop.browse(URI.create(url))
  } catch (e: Exception) {
    Logger.e(TAG, "Unable to open url. Reason: ${e.stackTrace}")
  }
}