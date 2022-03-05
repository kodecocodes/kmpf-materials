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

package com.raywenderlich.learn.data

import com.raywenderlich.learn.APP_NAME
import com.raywenderlich.learn.X_APP_NAME
import com.raywenderlich.learn.data.model.GravatarProfile
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import kotlin.native.concurrent.ThreadLocal

public const val GRAVATAR_URL = "https://en.gravatar.com/"
public const val GRAVATAR_RESPONSE_FORMAT = ".json"

@ThreadLocal
public object FeedAPI {

  private val nonStrictJson = Json { isLenient = true; ignoreUnknownKeys = true }

  private val client: HttpClient = HttpClient {

    defaultRequest {
      header(HttpHeaders.Accept, "text/html")
    }

    install(ContentNegotiation) {
      json(nonStrictJson)
    }

    install(Logging) {
      logger = HttpClientLogger
      level = LogLevel.ALL
    }
  }

  public suspend fun fetchRWEntry(feedUrl: String): HttpResponse = client.get(feedUrl)

  public suspend fun fetchImageUrlFromLink(link: String): HttpResponse = client.get(link)

  public suspend fun fetchMyGravatar(hash: String): GravatarProfile =
    client.get("$GRAVATAR_URL$hash$GRAVATAR_RESPONSE_FORMAT") {
      header(X_APP_NAME, APP_NAME)
    }.body()
}