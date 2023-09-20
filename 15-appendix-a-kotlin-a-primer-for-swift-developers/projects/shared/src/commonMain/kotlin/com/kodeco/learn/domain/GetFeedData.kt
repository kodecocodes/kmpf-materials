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
      feedUrl: String
  ): List<KodecoEntry> {
    return try {
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

      feed
    } catch (e: Exception) {
      Logger.e(TAG, "Unable to fetch feed:$feedUrl. Error: $e")
      emptyList()
    }
  }

  public suspend fun invokeFetchImageUrlFromLink(
      link: String
  ): String {
    return try {

      val result = FeedAPI.fetchImageUrlFromLink(link)
      parsePage(link, result.bodyAsText())

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
      GravatarEntry(
          thumbnailUrl = "data:image/jpeg;base64,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"
      )
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