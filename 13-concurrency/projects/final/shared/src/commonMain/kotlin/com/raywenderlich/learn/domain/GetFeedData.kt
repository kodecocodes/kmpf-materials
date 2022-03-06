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

package com.raywenderlich.learn.domain

import com.raywenderlich.learn.data.FeedAPI
import com.raywenderlich.learn.data.model.GravatarEntry
import com.raywenderlich.learn.data.model.PLATFORM
import com.raywenderlich.learn.data.model.RWEntry
import com.raywenderlich.learn.platform.Logger
import com.soywiz.korio.serialization.xml.Xml
import com.soywiz.korio.util.substringAfterOrNull
import com.soywiz.korio.util.substringBeforeOrNull
import io.ktor.client.statement.*
import kotlinx.coroutines.coroutineScope

private const val TAG = "GetFeedData"

private const val WEBSITE_PREVIEW_START_DELIMITER =
  "<img alt=\"\" class=\"c-tutorial-item__art-image--primary\" loading=\"lazy\" src=\""

private const val WEBSITE_PREVIEW_END_DELIMITER = "\" />"

public class GetFeedData {

  public suspend fun invokeFetchRWEntry(
    platform: PLATFORM,
    feedUrl: String,
    onSuccess: (List<RWEntry>) -> Unit,
    onFailure: (Exception) -> Unit
  ) {
    try {
      val result = FeedAPI.fetchRWEntry(feedUrl)

      Logger.d(TAG, "invokeFetchRWEntry | feedUrl=$feedUrl")
      val xml = Xml.parse(result.bodyAsText())

      val feed = mutableListOf<RWEntry>()
      for (node in xml.allNodeChildren) {
        val parsed = parseNode(platform, node)

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

  public suspend fun invokeFetchImageUrlFromLink(
      link: String
  ): String {
    return try {

      val result = FeedAPI.fetchImageUrlFromLink(link)
      parsePage(result.bodyAsText())

    } catch (e: Exception) {
      ""
    }
  }

  public suspend fun invokeGetMyGravatar(
    hash: String,
  ): GravatarEntry {
    return try {
      val result = FeedAPI.fetchMyGravatar(hash)
      Logger.d(TAG, "invokeGetMyGravatar | result=$result")

      if (result.entry.isEmpty()) {
        GravatarEntry()
      } else {
        result.entry[0]
      }
    } catch (e: Exception) {
      Logger.e(TAG, "Unable to fetch my gravatar. Error: $e")
      GravatarEntry()
    }
  }
}

private fun parsePage(content: String): String {
  val start =
    content.substringAfterOrNull(WEBSITE_PREVIEW_START_DELIMITER)
  val end = start?.substringBeforeOrNull(WEBSITE_PREVIEW_END_DELIMITER)
  return end ?: ""
}

private fun parseNode(platform: PLATFORM, entry: Xml): RWEntry? {
  if (entry.name == "entry") {
    val id = entry.allNodeChildren.firstOrNull { it.name == "id" }
    val link = entry.allNodeChildren.firstOrNull { it.name == "link" }
    val title = entry.allNodeChildren.firstOrNull { it.name == "title" }
    val summary = entry.allNodeChildren.firstOrNull { it.name == "summary" }
    val updated = entry.allNodeChildren.firstOrNull { it.name == "updated" }

    return RWEntry(
      id = id?.text ?: "",
      link = link?.attributesLC?.get("href") ?: "",
      title = title?.text ?: "",
      summary = summary?.text ?: "",
      updated = updated?.text ?: "",
      platform = platform
    )
  } else {
    return null
  }
}