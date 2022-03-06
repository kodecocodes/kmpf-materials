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
import com.raywenderlich.learn.data.model.RWEntry
import com.raywenderlich.learn.domain.GetFeedData
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

private const val RW_ALL_FEED = """
  [
  {
    "id": "https://www.raywenderlich.com/26244793-building-a-camera-app-with-swiftui-and-combine",
    "link": "https://www.raywenderlich.com/26244793-building-a-camera-app-with-swiftui-and-combine",
    "summary":"Learn to natively build your own SwiftUI camera app using Combine and create fun filters using the power of Core Image.",
    "title": "Building a Camera App With SwiftUI and Combine [FREE]",
    "updated": "2021-11-10T13:59:59Z"
  },

  {
    "id": "https://www.raywenderlich.com/29045204-announcing-swiftui-by-tutorials-fourth-edition",
    "link": "https://www.raywenderlich.com/29045204-announcing-swiftui-by-tutorials-fourth-edition",
    "summary":"Build apps more efficiently with SwiftUI’s declarative approach and leave the old ways of imperative coding in the dust, with this freshly-updated book.",
    "title": "Announcing SwiftUI by Tutorials, Fourth Edition! [FREE]",
    "updated": "2021-11-10T13:44:43Z"
  },

  {
    "id": "https://www.raywenderlich.com/books/swiftui-by-tutorialse",
    "link": "https://www.raywenderlich.com/books/swiftui-by-tutorials",
    "summary":"Build fluid and engaging declarative UI for your apps — using less code — with SwiftUI!</h2> <p>SwiftUI by Tutorials is designed to help you learn how to transition from the “old way” of building your app UI with UIKit, to the “new way” of building responsive UI with modern declarative syntax with SwiftUI. This book is for readers who are comfortable building Swift apps, and want to make the exciting leap into building their app UI with modern, declarative code.</p> <h3>What is SwiftUI?</h3> <p>SwiftUI lets you build better apps, faster, and with less code. It’s a developer’s dream come true! With SwiftUI, you can design your user interfaces in a declarative way; instead of developing app interfaces in an imperative way, by coding all of the application state logic before time, you can instead define what your app’s UI <em>should</em> do in a particular state and let the underlying OS figure out <em>how</em> to do it. What’s more is that SwiftUI lets you build modern, responsive UI and animations for <em>all</em> Apple devices — not just iOS. So whether you’re building apps for iOS, watchOS, tvOS or any other Apple platform, you can use the same concise, natural language to describe your UI and have it look stunning — no matter where your code runs. In addition, SwiftUI’s built-in automatic support for things such as dark mode, localization and accessibility, along with Xcode 11 support for drag-and-drop design and instant preview makes it easier to build apps than ever before.</p> <h3>How is this book different than SwiftUI Apprentice?</h3> <p>Our other book on getting started with SwiftUI, <a href=\"https://www.raywenderlich.com/books/swiftui-apprentice/\">SwiftUI Apprentice</a>, is designed to teach new developers how to build iOS apps, using a SwiftUI-first approach. The goal of that book is to teach you fundamental development practices as you build out some fully-functional and great-looking apps! This book, <em>SwiftUI by Tutorials</em>, is designed for developers who have a solid background in iOS development, and are looking to make the leap from building apps with UIKit, to building apps with SwiftUI.",
    "title": "SwiftUI by Tutorials [SUBSCRIBER]",
    "updated": "2021-11-10T00:00:00Z"
  },

  {
    "id": "https://www.raywenderlich.com/26933987-flutter-ui-widgets",
    "link": "https://www.raywenderlich.com/26933987-flutter-ui-widgets",
    "summary":"Explore commonly used UI widgets in Flutter and see how they relate to their native iOS and Android counterparts.",
    "title": "Flutter UI Widgets [SUBSCRIBER]",
    "updated": "2021-11-09T00:00:00Z"
  },

  {
    "id": "https://www.raywenderlich.com/26236685-shazamkit-tutorial-for-ios-getting-started",
    "link": "https://www.raywenderlich.com/26236685-shazamkit-tutorial-for-ios-getting-started",
    "summary":"Learn how to use ShazamKit to find information about specific audio recordings by matching a segment of that audio against a reference catalog of audio signatures.",
    "title": "ShazamKit Tutorial for iOS: Getting Started [FREE]",
    "updated": "2021-11-08T13:59:30Z"
  }
]
"""

class FeedPresenter(private val feed: GetFeedData) {

  private val json = Json { ignoreUnknownKeys = true }

  val content: List<RWContent> by lazy {
    json.decodeFromString(RW_CONTENT)
  }

  val allFeeds: List<RWEntry> by lazy {
    json.decodeFromString(RW_ALL_FEED)
  }
}