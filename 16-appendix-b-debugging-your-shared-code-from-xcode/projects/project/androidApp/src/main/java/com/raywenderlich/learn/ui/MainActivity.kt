/*
 * Copyright (c) 2021 Razeware LLC
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

package com.raywenderlich.learn.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.livedata.observeAsState
import com.raywenderlich.learn.R
import com.raywenderlich.learn.ui.bookmark.BookmarkViewModel
import com.raywenderlich.learn.ui.home.FeedViewModel
import com.raywenderlich.learn.ui.main.MainScreen
import com.raywenderlich.learn.ui.theme.RWTheme
import com.raywenderlich.learn.data.model.RWEntry

class MainActivity : AppCompatActivity() {

  private val bookmarkViewModel: BookmarkViewModel by viewModels()
  private val feedViewModel: FeedViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    feedViewModel.fetchAllFeeds()
    feedViewModel.fetchMyGravatar()
    bookmarkViewModel.getBookmarks()

    setContent {

      val items = feedViewModel.items
      val profile = feedViewModel.profile.observeAsState()
      if (!profile.value?.preferredUsername.isNullOrEmpty()) {
        val name = profile.value!!.preferredUsername
        Toast.makeText(
          applicationContext,
          getString(R.string.action_hello, name),
          Toast.LENGTH_SHORT).
        show()
      }

      val bookmarks = bookmarkViewModel.items.observeAsState()

      RWTheme {
        MainScreen(
          feeds = items,
          bookmarks = bookmarks,
          onUpdateBookmark = { onUpdateBookmark(it) },
          onShareAsLink = { shareAsLink(it) },
          onOpenEntry = { openEntry(it) }
        )
      }
    }
  }

  private fun onUpdateBookmark(item: RWEntry) {
    if (item.bookmarked) {
      removedFromBookmarks(item)
    } else {
      addToBookmarks(item)
    }
  }

  private fun addToBookmarks(item: RWEntry) {
    Toast.makeText(applicationContext, R.string.action_added_bookmarks, Toast.LENGTH_SHORT).show()
    bookmarkViewModel.addAsBookmark(item)
    bookmarkViewModel.getBookmarks()
  }

  private fun removedFromBookmarks(item: RWEntry) {
    Toast.makeText(applicationContext, R.string.action_removed_bookmarks, Toast.LENGTH_SHORT).show()
    bookmarkViewModel.removeFromBookmark(item)
    bookmarkViewModel.getBookmarks()
  }

  private fun shareAsLink(item: RWEntry) {
    val sendIntent: Intent = Intent().apply {
      action = Intent.ACTION_SEND
      putExtra(Intent.EXTRA_TEXT, getString(R.string.action_share_link_text, item.link))
      type = "text/plain"
    }

    startActivity(Intent.createChooser(sendIntent, null))
  }

  private fun openEntry(url: String) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(url)
    startActivity(intent)
  }
}