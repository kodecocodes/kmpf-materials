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

package com.raywenderlich.learn

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.raywenderlich.learn.action.Action.openLink
import com.raywenderlich.learn.components.Toast
import com.raywenderlich.learn.data.model.RWEntry
import com.raywenderlich.learn.ui.bookmark.BookmarkViewModel
import com.raywenderlich.learn.ui.home.FeedViewModel
import com.raywenderlich.learn.ui.main.MainScreen
import com.raywenderlich.learn.ui.theme.RWTheme
import moe.tlaster.precompose.PreComposeWindow
import moe.tlaster.precompose.ui.viewModel

private lateinit var bookmarkViewModel: BookmarkViewModel
private lateinit var feedViewModel: FeedViewModel

private const val TAG = "main"

private val showToast: MutableState<Boolean> = mutableStateOf(false)

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

      val name = profile.value.preferredUsername
      if (!name.isNullOrEmpty()) {
        showToast.value = true
      }

      val bookmarks = bookmarkViewModel.items

      Surface(modifier = Modifier.fillMaxSize()) {

        RWTheme {
          MainScreen(
            feeds = items,
            bookmarks = bookmarks,
            onUpdateBookmark = { updateBookmark(it) },
            onShareAsLink = {},
            onOpenEntry = { openLink(it) }
          )
        }

        Toast("Hello $name", showToast)
      }
    }
  }
}

private fun updateBookmark(item: RWEntry) {
  if (item.bookmarked) {
    removedFromBookmarks(item)
  } else {
    addToBookmarks(item)
  }
}

private fun addToBookmarks(item: RWEntry) {
  bookmarkViewModel.addAsBookmark(item)
  bookmarkViewModel.getBookmarks()
}

private fun removedFromBookmarks(item: RWEntry) {
  bookmarkViewModel.removeFromBookmark(item)
  bookmarkViewModel.getBookmarks()
}