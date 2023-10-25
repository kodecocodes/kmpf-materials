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

package com.kodeco.learn.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.kodeco.learn.data.model.KodecoEntry
import com.kodeco.learn.ui.bookmark.BookmarkViewModel
import com.kodeco.learn.ui.home.FeedViewModel
import com.kodeco.learn.ui.main.MainScreen
import com.kodeco.learn.ui.theme.KodecoTheme
import moe.tlaster.precompose.PreComposeApplication
import moe.tlaster.precompose.viewmodel.viewModel
import platform.Foundation.NSLog
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

private lateinit var bookmarkViewModel: BookmarkViewModel
private lateinit var feedViewModel: FeedViewModel

fun MainViewController() = PreComposeApplication {
  Surface(modifier = Modifier.fillMaxSize()) {

    bookmarkViewModel = viewModel(BookmarkViewModel::class) {
      BookmarkViewModel()
    }

    feedViewModel = viewModel(FeedViewModel::class) {
      FeedViewModel()
    }

    feedViewModel.fetchAllFeeds()
    feedViewModel.fetchMyGravatar()
    bookmarkViewModel.getBookmarks()

    val items = feedViewModel.items
    val profile = feedViewModel.profile
    val bookmarks = bookmarkViewModel.items

    KodecoTheme {
      MainScreen(
          profile = profile.value,
          feeds = items,
          bookmarks = bookmarks,
          onUpdateBookmark = { updateBookmark(it) },
          onShareAsLink = {},
          onOpenEntry = { openLink(it) }
      )
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

private fun openLink(url: String) {
  val application = UIApplication.sharedApplication
  val nsurl = NSURL(string = url)
  if (!application.canOpenURL(nsurl)) {
    NSLog("Unable to open url: $url")
    return
  }

  application.openURL(nsurl)
}
