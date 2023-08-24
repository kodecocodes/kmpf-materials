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

package com.kodeco.learn.presentation

import com.kodeco.learn.data.model.GravatarEntry
import com.kodeco.learn.data.model.KodecoContent
import com.kodeco.learn.data.model.PLATFORM
import com.kodeco.learn.domain.GetFeedData
import com.kodeco.learn.domain.cb.FeedData
import com.kodeco.learn.platform.Logger
import korlibs.crypto.md5
import korlibs.io.lang.toByteArray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

private const val TAG = "FeedPresenter"

private const val KODECO_CONTENT = "[" +
    "{\"platform\":\"all\", \"url\":\"https://www.kodeco.com/feed.xml\", \"image\":\"https://play-lh.googleusercontent.com/CAa4g9UbOJambautjl7lOfdiwjYoX04ORbivxdkPDZNirQd23TXQAfbFYPTN1VBWyzDt\"}," +
    "{\"platform\":\"android\", \"url\":\"https://kodeco.com/android/feed\", \"image\":\"https://files.carolus.kodeco.com/aqye8zuk8vgzkswjog6b0gcbbvk5?response_content_disposition=inline%3B+filename%3D%22Android.png%22%3B+filename%2A%3DUTF-8%27%27Android.png\"}," +
    "{\"platform\":\"ios\", \"url\":\"https://kodeco.com/ios/feed\", \"image\":\"https://files.carolus.kodeco.com/gjf3gnowf4bxsca51sp6umwtxv1z?response_content_disposition=inline%3B+filename%3D%22iOS.png%22%3B+filename%2A%3DUTF-8%27%27iOS.png\"}," +
    "{\"platform\":\"flutter\", \"url\":\"https://kodeco.com/flutter/feed\", \"image\":\"https://files.carolus.kodeco.com/sdf7m85b1ekaggr6i93i8yqzt8g6?response_content_disposition=inline%3B+filename%3D%22Flutter.png%22%3B+filename%2A%3DUTF-8%27%27Flutter.png\"}," +
    "{\"platform\":\"server\", \"url\":\"https://kodeco.com/server-side-swift/feed\", \"image\":\"https://files.carolus.kodeco.com/pknmfaooyqdaz6rmoqawxflb5fit?response_content_disposition=inline%3B+filename%3D%22SSS.png%22%3B+filename%2A%3DUTF-8%27%27SSS.png\"}," +
    "{\"platform\":\"gametech\", \"url\":\"https://kodeco.com/gametech/feed\", \"image\":\"https://files.carolus.kodeco.com/drlaqjkqbxk7d65nqm8e03cdpul7?response_content_disposition=inline%3B+filename%3D%22GameTech.png%22%3B+filename%2A%3DUTF-8%27%27GameTech.png\"}," +
    "{\"platform\":\"growth\", \"url\":\"https://kodeco.com/professional-growth/feed\", \"image\":\"https://files.carolus.kodeco.com/gl22boss4ptciv7px3nnzcnct4ht?response_content_disposition=inline%3B+filename%3D%22progro.png%22%3B+filename%2A%3DUTF-8%27%27progro.png\"}" +
    "]"

private const val GRAVATAR_EMAIL = "cafonsomota@gmail.com"

class FeedPresenter(private val feed: GetFeedData) {

  private val scope = CoroutineScope(Dispatchers.IO)

  private val json = Json { ignoreUnknownKeys = true }

  val content: List<KodecoContent> by lazy {
    json.decodeFromString(KODECO_CONTENT)
  }

  public fun fetchAllFeeds(cb: FeedData) {
    Logger.d(TAG, "fetchAllFeeds")

    for (feed in content) {
      fetchFeed(feed.platform, feed.image, feed.url, cb)
    }
  }

  private fun fetchFeed(
    platform: PLATFORM,
    platformImageUrl: String,
    feedUrl: String,
    cb: FeedData
  ) {
    scope.launch {
      feed.invokeFetchKodecoEntry(
        platform = platform,
        platformImageUrl = platformImageUrl,
        feedUrl = feedUrl,
        onSuccess = { cb.onNewDataAvailable(it, platform, null) },
        onFailure = { cb.onNewDataAvailable(emptyList(), platform, it) }
      )
    }
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
        hash = GRAVATAR_EMAIL.toByteArray().md5().toString()
      )
    }.await()
  }
}