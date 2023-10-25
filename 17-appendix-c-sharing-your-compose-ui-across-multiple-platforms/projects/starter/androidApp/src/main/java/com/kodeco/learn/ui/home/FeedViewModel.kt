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

package com.kodeco.learn.ui.home

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodeco.learn.ServiceLocator
import com.kodeco.learn.data.model.GravatarEntry
import com.kodeco.learn.data.model.KodecoEntry
import com.kodeco.learn.data.model.PLATFORM
import com.kodeco.learn.domain.cb.FeedData
import com.kodeco.learn.logger.Logger
import kotlinx.coroutines.launch

private const val TAG = "FeedViewModel"

class FeedViewModel : ViewModel(), FeedData {

  private val _items = mutableStateMapOf<PLATFORM, List<KodecoEntry>>()
  val items: SnapshotStateMap<PLATFORM, List<KodecoEntry>> = _items

  private val _profile = MutableLiveData(GravatarEntry())
  val profile: MutableLiveData<GravatarEntry> = _profile

  private val presenter by lazy {
    ServiceLocator.getFeedPresenter
  }

  fun fetchAllFeeds() {
    Logger.d(TAG, "fetchAllFeeds")
    viewModelScope.launch {
      presenter.fetchAllFeeds().collect {
        val platform = it.first().platform
        _items[platform] = it

        for (item in _items[platform]!!) {
          fetchLinkImage(platform, item.id, item.link)
        }
      }
    }
  }

  private fun fetchLinkImage(platform: PLATFORM, id: String, link: String) {
    Logger.d(TAG, "fetchLinkImage | link=$link")
    viewModelScope.launch {
      val url = presenter.fetchLinkImage(link)

      val item = _items[platform]?.firstOrNull { it.id == id } ?: return@launch
      val list = _items[platform]?.toMutableList() ?: return@launch
      val index = list.indexOf(item)

      list[index] = item.copy(imageUrl = url)
      _items[platform] = list
    }
  }

  fun fetchMyGravatar() {
    Logger.d(TAG, "fetchMyGravatar")
    presenter.fetchMyGravatar(this)
  }

  // region FeedData

  override fun onMyGravatarData(item: GravatarEntry) {
    Logger.d(TAG, "onMyGravatarData | item=$item")
    viewModelScope.launch {
      _profile.value = item
    }
  }

  // endregion FeedData
}
