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

import com.raywenderlich.learn.data.model.RWContent
import com.raywenderlich.learn.domain.GetFeedData

private const val TAG = "FeedPresenter"

private const val RW_CONTENT = "[" +
    "{\"platform\":\"all\", \"url\":\"https://www.raywenderlich.com/feed.xml\", \"image\":\"https://assets.carolus.raywenderlich.com/assets/razeware_460-308933a0bda63e3e327123cab8002c0383a714cd35a10ade9bae9ca20b1f438b.png\"}," +
    "{\"platform\":\"android\", \"url\":\"https://raywenderlich.com/android/feed\", \"image\":\"https://koenig-media.raywenderlich.com/uploads/2017/11/android-love-1-1.png\"}," +
    "{\"platform\":\"ios\", \"url\":\"https://raywenderlich.com/ios/feed\", \"image\":\"https://koenig-media.raywenderlich.com/uploads/2018/09/iOS12_LaunchParty-feature.png\"}," +
    "{\"platform\":\"unity\", \"url\":\"https://raywenderlich.com/gametech/feed\", \"image\":\"https://koenig-media.raywenderlich.com/uploads/2021/03/Unity2D-feature.png\"}," +
    "{\"platform\":\"flutter\", \"url\":\"https://raywenderlich.com/flutter/feed\", \"image\":\"https://koenig-media.raywenderlich.com/uploads/2018/11/OpenCall-Android-Flutter-Book-feature.png\"}" +
    "]"

class FeedPresenter(private val feed: GetFeedData) {

  val content: List<RWContent> by lazy {
    emptyList()
  }
}