/// Copyright (c) 2023 Kodeco Inc
///
/// Permission is hereby granted, free of charge, to any person obtaining a copy
/// of this software and associated documentation files (the "Software"), to deal
/// in the Software without restriction, including without limitation the rights
/// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
/// copies of the Software, and to permit persons to whom the Software is
/// furnished to do so, subject to the following conditions:
///
/// The above copyright notice and this permission notice shall be included in
/// all copies or substantial portions of the Software.
///
/// Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
/// distribute, sublicense, create a derivative work, and/or sell copies of the
/// Software in any work that is designed, intended, or marketed for pedagogical or
/// instructional purposes related to programming, coding, application development,
/// or information technology.  Permission for such use, copying, modification,
/// merger, publication, distribution, sublicensing, creation of derivative works,
/// or sale is expressly withheld.
///
/// This project and source code may use libraries or frameworks that are
/// released under various Open-Source licenses. Use of those libraries and
/// frameworks are governed by their own individual licenses.
///
/// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
/// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
/// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
/// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
/// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
/// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
/// THE SOFTWARE.

import SwiftUI
import SharedKit
import SharedLogger

let TAG = "KodecoEntryViewModel"

class KodecoEntryViewModel: ObservableObject {
  @Published var items: [String: [KodecoEntry]] = [:]

  @Published var bookmarks: [KodecoEntry] = []

  @Published var profile: GravatarEntry?

  let fetchNImages = 5

  init() {
    fetchProfile()
    fetchFeeds()
  }

  func getContent() -> [KodecoContent] {
    FeedClient.shared.getContent()
  }

  func fetchProfile() {
    Task {
      guard let profile = await FeedClient.shared.fetchProfile() else { return }
      DispatchQueue.main.async {
        self.profile = profile
      }
    }
  }

  func fetchFeeds() {
    Task {
      let test = await FeedClient.shared.fetchFeeds()
      DispatchQueue.main.async {
        self.items = test
        self.fetchLinkImage()
      }
    }
  }

  @MainActor
  func fetchLinkImage() {
    for platform in self.items.keys {
      guard let items = self.items[platform] else { continue }
      let subsetItems = Array(items[0 ..< Swift.min(self.fetchNImages, items.count)])
      for item in subsetItems {
        FeedClient.shared.fetchLinkImage(item.link) { url in
          guard var list = self.items[platform.description] else {
            return
          }
          guard let index = list.firstIndex(of: item) else {
            return
          }

          list[index] = item.doCopy(
            id: item.id,
            link: item.link,
            title: item.title,
            summary: item.summary,
            updated: item.updated,
            platform: item.platform,
            imageUrl: url,
            bookmarked: item.bookmarked
          )

          self.items[platform.description] = list
        }
      }
    }
  }

  func fetchAllBookmarks() {
    BookmarkClient.shared.fetchBookmarks { items in
      Logger().d(tag: TAG, message: "fetchAllBookmarks: \(items.count) items")
      DispatchQueue.main.async {
        self.bookmarks = items
      }
    }
  }

  func addToBookmarks(entry: KodecoEntry) {
    BookmarkClient.shared.addToBookmarks(entry) { _ in
      Logger().d(tag: TAG, message: "addToBookmarks")
    }
  }

  func removeFromBookmarks(entry: KodecoEntry) {
    BookmarkClient.shared.removeFromBookmarks(entry) { _ in
      Logger().d(tag: TAG, message: "removeFromBookmarks")
    }
  }
}
