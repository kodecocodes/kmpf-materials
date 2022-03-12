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

import com.raywenderlich.learn.data.RWSerializer
import com.raywenderlich.learn.data.model.PLATFORM
import com.raywenderlich.learn.data.model.RWContent
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.serializersModuleOf
import kotlinx.serialization.serializer

class SerializationTests {

  private val serializers = serializersModuleOf(PLATFORM::class, RWSerializer)

  @Test
  fun testEncodePlatformAll() {
    val data = RWContent(
      platform = PLATFORM.ALL,
      url = "https://www.raywenderlich.com/feed.xml",
      image = "https://assets.carolus.raywenderlich.com/assets/razeware_460-308933a0bda63e3e327123cab8002c0383a714cd35a10ade9bae9ca20b1f438b.png"
    )

    val decoded = Json.encodeToString(RWContent.serializer(), data)

    val content =
      "{\"platform\":\"all\",\"url\":\"https://www.raywenderlich.com/feed.xml\",\"image\":\"https://assets.carolus.raywenderlich.com/assets/razeware_460-308933a0bda63e3e327123cab8002c0383a714cd35a10ade9bae9ca20b1f438b.png\"}"
    assertEquals(content, decoded)
  }

  @Test
  fun testDecodePlatformAll() {
    val data =
      "{\"platform\":\"all\",\"url\":\"https://www.raywenderlich.com/feed.xml\",\"image\":\"https://assets.carolus.raywenderlich.com/assets/razeware_460-308933a0bda63e3e327123cab8002c0383a714cd35a10ade9bae9ca20b1f438b.png\"}"

    val decoded = Json.decodeFromString(RWContent.serializer(), data)
    val content = RWContent(
      platform = PLATFORM.ALL,
      url = "https://www.raywenderlich.com/feed.xml",
      image = "https://assets.carolus.raywenderlich.com/assets/razeware_460-308933a0bda63e3e327123cab8002c0383a714cd35a10ade9bae9ca20b1f438b.png"
    )

    assertEquals(content, decoded)
  }

  @Test
  fun testEncodeCustomPlatformAll() {
    val data = PLATFORM.ALL

    val encoded = Json.encodeToString(serializers.serializer(), data)
    val expectedString = "\"all\""
    assertEquals(expectedString, encoded)
  }

  @Test
  fun testDecodeCustomPlatformAll() {
    val data = PLATFORM.ALL

    val decoded = Json.decodeFromString<PLATFORM>(data.value)
    assertEquals(decoded, data)
  }
}