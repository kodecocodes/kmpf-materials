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

package com.raywenderlich.learn

import com.raywenderlich.learn.data.GRAVATAR_RESPONSE_FORMAT
import com.raywenderlich.learn.data.GRAVATAR_URL
import com.raywenderlich.learn.data.model.GravatarEntry
import com.raywenderlich.learn.data.model.GravatarProfile
import io.ktor.client.HttpClient
import io.ktor.client.call.*
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class NetworkTests {

  private val profile = GravatarProfile(
    entry = listOf(
      GravatarEntry(
        id = "1000",
        hash = "1000",
        preferredUsername = "Ray Wenderlich",
        thumbnailUrl = "https://avatars.githubusercontent.com/u/4722515?s=200&v=4"
      )
    )
  )

  private val nonStrictJson = Json { isLenient = true; ignoreUnknownKeys = true }

  private fun getHttpClient(): HttpClient {
    return HttpClient(MockEngine) {

      defaultRequest {
        header(HttpHeaders.Accept, "text/html")
      }

      install(ContentNegotiation) {
        json(nonStrictJson)
      }

      engine {
        addHandler { request ->
          if (request.url.toString().contains(GRAVATAR_URL)) {
            val respond = respond(
              content = Json.encodeToString(profile),
              headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
            respond
          }
          else {
            error("Unhandled ${request.url}")
          }
        }
      }
    }
  }

  @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
  @Test
  public fun testFetchMyGravatar() = kotlinx.coroutines.test.runTest {
    val client = getHttpClient()
    assertEquals(profile, client.request
      ("$GRAVATAR_URL${profile.entry[0].hash}$GRAVATAR_RESPONSE_FORMAT").body())
  }
}