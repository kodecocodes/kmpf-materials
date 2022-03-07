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

package com.raywenderlich.learn.ui.bookmark

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.raywenderlich.learn.ServiceLocator
import com.raywenderlich.learn.data.model.RWEntry
import com.raywenderlich.learn.domain.cb.BookmarkData
import com.raywenderlich.learn.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moe.tlaster.precompose.lifecycle.Lifecycle
import moe.tlaster.precompose.livedata.LiveData
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

private const val TAG = "BookmarkViewModel"

class BookmarkViewModel : ViewModel(), BookmarkData {

  val item = LiveData(RWEntry())
  val items: MutableState<List<RWEntry>> = mutableStateOf(emptyList())

  private val presenter by lazy {
    ServiceLocator.getBookmarkPresenter
  }

  fun getBookmarks() {
    Logger.d(TAG, "getBookmarks")
    presenter.getBookmarks(this)
  }

  fun addAsBookmark(entry: RWEntry) {
    Logger.d(TAG, "addAsBookmark")
    presenter.addAsBookmark(entry, this)
  }

  fun removeFromBookmark(entry: RWEntry) {
    Logger.d(TAG, "removeFromBookmark")
    presenter.removeFromBookmark(entry, this)
  }

  // region FeedData

  override fun onNewBookmarksList(newItems: List<RWEntry>) {
    Logger.d(TAG, "onNewBookmarksList | items=${newItems.size}")
    viewModelScope.launch {
      withContext(Dispatchers.Main) {
        items.value = newItems
      }
    }
  }

  override fun onBookmarkStateUpdated(newItem: RWEntry, added: Boolean) {
    Logger.d(TAG, "onBookmarkStateUpdated | newItem=$newItem | added=$added")
    viewModelScope.launch {
      withContext(Dispatchers.Main) {
        item.value = newItem
        getBookmarks()
      }
    }
  }

  // endregion FeedData
}
