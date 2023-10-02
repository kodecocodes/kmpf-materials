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

package com.kodeco.learn.domain

import com.kodeco.learn.data.FeedAPI
import com.kodeco.learn.data.model.GravatarEntry
import com.kodeco.learn.data.model.KodecoEntry
import com.kodeco.learn.data.model.PLATFORM
import com.kodeco.learn.platform.Logger
import io.ktor.client.statement.bodyAsText
import korlibs.io.serialization.xml.Xml
import korlibs.io.util.substringAfterOrNull
import korlibs.io.util.substringBeforeOrNull
import kotlinx.coroutines.coroutineScope

private const val TAG = "GetFeedData"

private const val WEBSITE_PREVIEW_ARTICLE_START_DELIMITER = "<div class=\"triad-top-right\">\n        <img src=\""

private const val WEBSITE_PREVIEW_ARTICLE_END_DELIMITER = "\" />"

private const val WEBSITE_PREVIEW_BOOK_START_DELIMITER =
  "<img alt=\"\" class=\"c-tutorial-item__art-image--primary\" loading=\"lazy\" src=\""

private const val WEBSITE_PREVIEW_BOOK_END_DELIMITER = "\" />"

public class GetFeedData {

  public suspend fun invokeFetchKodecoEntry(
      platform: PLATFORM,
      imageUrl: String,
      feedUrl: String,
      onSuccess: (List<KodecoEntry>) -> Unit,
      onFailure: (Exception) -> Unit
  ) {
    try {
      val result = FeedAPI.fetchKodecoEntry(feedUrl)

      Logger.d(TAG, "invokeFetchKodecoEntry | feedUrl=$feedUrl")

      val xml = Xml.parse(result.bodyAsText())

      val feed = mutableListOf<KodecoEntry>()
      for (node in xml.allNodeChildren) {
        val parsed = parseNode(platform, imageUrl, node)

        if (parsed != null) {
          feed += parsed
        }
      }

      coroutineScope {
        onSuccess(feed)
      }
    } catch (e: Exception) {
      Logger.e(TAG, "Unable to fetch feed:$feedUrl. Error: $e")
      coroutineScope {
        onFailure(e)
      }
    }
  }

  public suspend fun invokeGetMyGravatar(
      hash: String,
      onSuccess: (GravatarEntry) -> Unit,
      onFailure: (Exception) -> Unit
  ) {
    try {
      val result = FeedAPI.fetchMyGravatar(hash)
      Logger.d(TAG, "invokeGetMyGravatar | result=$result")

      if (result.entry.isEmpty()) {
        coroutineScope {
          onFailure(Exception("No profile found for hash=$hash"))
        }
      } else {
        coroutineScope {
          onSuccess(result.entry[0])
        }
      }
    } catch (e: Exception) {
      Logger.e(TAG, "Unable to fetch my gravatar. Error: $e")
      coroutineScope {
        onFailure(e)
      }
    }
  }
}

private fun parsePage(url: String, content: String): String {
  val start = if (url.contains("books", true)) {
    content.substringAfterOrNull(WEBSITE_PREVIEW_BOOK_START_DELIMITER)
  } else {
    content.substringAfterOrNull(WEBSITE_PREVIEW_ARTICLE_START_DELIMITER)
  }
  val end = if (url.contains("books", true)) {
    start?.substringBeforeOrNull(WEBSITE_PREVIEW_BOOK_END_DELIMITER)
 } else {
    start?.substringBeforeOrNull(WEBSITE_PREVIEW_ARTICLE_END_DELIMITER)
  }
  return end ?: ""
}

private fun parseNode(platform: PLATFORM, imageUrl: String, entry: Xml): KodecoEntry? {
  if (entry.name == "entry") {
    val id = entry.allNodeChildren.firstOrNull { it.name == "id" }
    val link = entry.allNodeChildren.firstOrNull { it.name == "link" }
    val title = entry.allNodeChildren.firstOrNull { it.name == "title" }
    val summary = entry.allNodeChildren.firstOrNull { it.name == "summary" }
    val updated = entry.allNodeChildren.firstOrNull { it.name == "updated" }

    return KodecoEntry(
      id = id?.text ?: "",
      link = link?.attributesLC?.get("href") ?: "",
      title = title?.text ?: "",
      summary = summary?.text ?: "",
      updated = updated?.text ?: "",
      platform = platform,
      imageUrl = imageUrl
    )
  } else {
    return null
  }
}