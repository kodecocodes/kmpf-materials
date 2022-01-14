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

package com.raywenderlich.learn.ui.home

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import com.raywenderlich.learn.ServiceLocator
import com.raywenderlich.learn.data.model.PLATFORM
import com.raywenderlich.learn.data.model.RWEntry
import com.raywenderlich.learn.domain.cb.FeedData
import com.raywenderlich.learn.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

private const val TAG = "FeedViewModel"

private const val FETCH_N_IMAGES = 5

class FeedViewModel : ViewModel(), FeedData {

  val items: SnapshotStateMap<PLATFORM, List<RWEntry>> = mutableStateMapOf()

  private val presenter by lazy {
    ServiceLocator.getFeedPresenter
  }

  fun fetchAllFeeds() {
    Logger.d(TAG, "fetchAllFeeds")
    presenter.fetchAllFeeds(this)
  }

  private fun fetchLinkImage(platform: PLATFORM, id: String, link: String) {
    Logger.d(TAG, "fetchLinkImage | link=$link")
    presenter.fetchLinkImage(platform, id, link, this)
  }

  // region FeedData

  override fun onNewDataAvailable(newItems: List<RWEntry>, platform: PLATFORM, e: Exception?) {
    Logger.d(TAG, "onNewDataAvailable | platform=$platform items=${items.size}")
    viewModelScope.launch {
      withContext(Dispatchers.Main) {
        items[platform] = newItems.subList(0, FETCH_N_IMAGES)

        for (item in items[platform]!!) {
          fetchLinkImage(platform, item.id, item.link)
        }
      }
    }
  }

  override fun onNewImageUrlAvailable(id: String, url: String, platform: PLATFORM, e: Exception?) {
    Logger.d(TAG, "onNewImageUrlAvailable | platform=$platform | id=$id | url=$url")
    viewModelScope.launch {
      withContext(Dispatchers.Main) {

        Logger.d(TAG, "items=${items.keys}")

        val item = items[platform]?.firstOrNull { it.id == id } ?: return@withContext
        val list = items[platform]?.toMutableList() ?: return@withContext
        val index = list.indexOf(item)

        list[index] = item.copy(imageUrl = url)
        items[platform] = list
      }
    }
  }

  // endregion FeedData
}
