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

import com.kodeco.learn.data.GRAVATAR_RESPONSE_FORMAT
import com.kodeco.learn.data.GRAVATAR_URL
import com.kodeco.learn.data.model.GravatarEntry
import com.kodeco.learn.data.model.GravatarProfile
import com.kodeco.learn.platform.runTest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.request
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class NetworkTests {

  private val profile = GravatarProfile(
      entry = listOf(
          GravatarEntry(
              id = "1000",
              hash = "1000",
              preferredUsername = "Kodeco",
              thumbnailUrl = "https://avatars.githubusercontent.com/u/4722515?s=200&v=4"
          )
      )
  )

  private val nonStrictJson = Json { isLenient = true; ignoreUnknownKeys = true }

  private fun getHttpClient(): HttpClient {
    return HttpClient(MockEngine) {

      install(ContentNegotiation) {
        json(nonStrictJson)
      }

      engine {
        addHandler { request ->
          if (request.url.toString().contains(GRAVATAR_URL)) {
            respond(
                content = Json.encodeToString(profile),
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()))
          }
          else {
            error("Unhandled ${request.url}")
          }
        }
      }
    }
  }

  @Test
  public fun testFetchMyGravatar() = runTest {
    val client = getHttpClient()
    assertEquals(profile, client.request
    ("$GRAVATAR_URL${profile.entry[0].hash}$GRAVATAR_RESPONSE_FORMAT").body())
  }
}