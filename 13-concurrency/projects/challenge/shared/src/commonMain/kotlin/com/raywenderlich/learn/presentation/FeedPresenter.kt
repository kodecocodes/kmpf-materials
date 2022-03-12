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

package com.raywenderlich.learn.presentation

import com.raywenderlich.learn.data.model.GravatarEntry
import com.raywenderlich.learn.data.model.PLATFORM
import com.raywenderlich.learn.data.model.RWContent
import com.raywenderlich.learn.domain.GetFeedData
import com.raywenderlich.learn.domain.cb.FeedData
import com.raywenderlich.learn.domain.ioDispatcher
import com.raywenderlich.learn.md5
import com.raywenderlich.learn.platform.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

private const val TAG = "FeedPresenter"

private const val RW_CONTENT = "[" +
    "{\"platform\":\"all\", \"url\":\"https://www.raywenderlich.com/feed.xml\", \"image\":\"https://assets.carolus.raywenderlich.com/assets/razeware_460-308933a0bda63e3e327123cab8002c0383a714cd35a10ade9bae9ca20b1f438b.png\"}," +
    "{\"platform\":\"android\", \"url\":\"https://raywenderlich.com/android/feed\", \"image\":\"https://koenig-media.raywenderlich.com/uploads/2017/11/android-love-1-1.png\"}," +
    "{\"platform\":\"ios\", \"url\":\"https://raywenderlich.com/ios/feed\", \"image\":\"https://koenig-media.raywenderlich.com/uploads/2018/09/iOS12_LaunchParty-feature.png\"}," +
    "{\"platform\":\"unity\", \"url\":\"https://raywenderlich.com/gametech/feed\", \"image\":\"https://koenig-media.raywenderlich.com/uploads/2021/03/Unity2D-feature.png\"}," +
    "{\"platform\":\"flutter\", \"url\":\"https://raywenderlich.com/flutter/feed\", \"image\":\"https://koenig-media.raywenderlich.com/uploads/2018/11/OpenCall-Android-Flutter-Book-feature.png\"}" +
    "]"

private const val GRAVATAR_EMAIL = "YOUR_GRAVATAR_EMAIL"

private const val FETCH_N_IMAGES = 5

class FeedPresenter(private val feed: GetFeedData) {

  private val scope = CoroutineScope(ioDispatcher)

  private val json = Json { ignoreUnknownKeys = true }

  val content: List<RWContent> by lazy {
    json.decodeFromString(RW_CONTENT)
  }

  public fun fetchAllFeeds(cb: FeedData) {
    Logger.d(TAG, "fetchAllFeeds")

    for (feed in content) {
      fetchFeed(feed.platform, feed.url, cb)
    }
  }

  private fun fetchFeed(platform: PLATFORM, feedUrl: String, cb: FeedData) {
    scope.launch {
      val entries = feed.invokeFetchRWEntry(
        platform = platform,
        feedUrl = feedUrl,
      )?.subList(0, FETCH_N_IMAGES) ?: return@launch

      val tasks = mutableListOf<Job>()
      for (entry in entries) {
        tasks.add(scope.launch {
          entry.imageUrl = fetchLinkImage(entry.link)
        })
      }

      tasks.joinAll()
      cb.onNewDataAvailable(entries, platform, null)
    }
  }

  public suspend fun fetchLinkImage(link: String): String {
    return scope.async {
      feed.invokeFetchImageUrlFromLink(
        link
      )
    }.await()
  }

  public fun fetchMyGravatar(cb: FeedData) {
    Logger.d(TAG, "fetchMyGravatar")

    CoroutineScope(Dispatchers.Default).launch {
      cb.onMyGravatarData(fetchMyGravatar())
    }
  }

  private suspend fun fetchMyGravatar(): GravatarEntry {
    return CoroutineScope(Dispatchers.Default).async {
      feed.invokeGetMyGravatar(
        hash = md5(GRAVATAR_EMAIL)
      )
    }.await()
  }
}