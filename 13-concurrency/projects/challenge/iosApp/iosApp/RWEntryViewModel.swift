/// Copyright (c) 2021 Razeware LLC
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

let TAG = "RWEntryViewModel"

class RWEntryViewModel: ObservableObject {
        
    @Published var items = [String:[RWEntry]]()
    
    @Published var bookmarks = [RWEntry]()
    
    @Published var profile = GravatarEntry(
        id: nil,
        hash: nil,
        preferredUsername: nil,
        thumbnailUrl: nil,
        aboutMe: nil
    )
    
    let FETCH_N_IMAGES = 5
    
    func getContent() -> [RWContent] {
        return FeedClient.getContent()
    }
    
    func fetchProfile() {
        return FeedClient.fetchProfile { profile in
            Logger().d(tag: TAG, message: "fetchProfile: \(profile)")
            self.profile = profile
        }
    }
 
    func fetchFeeds() {
        FeedClient.fetchFeeds { platform, items in
            Logger().d(tag: TAG, message: "fetchFeeds: \(items.count) items | platform: \(platform)")
            self.items[platform] = items
        }
    }
    
    @MainActor
    func fetchFeedsWithPreview() {
        FeedClient.fetchFeeds { platform, items in
            Logger().d(tag: TAG, message: "fetchFeeds: \(items.count) items | platform: \(platform)")
            self.items[platform] = items
            
            let subsetItems = Array(items[0 ..< Swift.min(self.FETCH_N_IMAGES, items.count)])
            for item in subsetItems {
                FeedClient.fetchLinkImage(link: item.link, completion: { url in
                    var list = self.items[platform.description]
                    let index = list?.firstIndex(of: item)
                    
                    list![index!] = item.doCopy(
                        id: item.id,
                        link: item.link,
                        title: item.title,
                        summary: item.summary,
                        updated: item.updated,
                        imageUrl: url,
                        platform: item.platform,
                        bookmarked: item.bookmarked
                    )
                    
                    Logger().d(tag: TAG, message: "\(list![index!].title)Updated to:\(list![index!].imageUrl)")
                    
                    self.items[platform.description] = list
                })
            }
        }
    }
    
    func fetchAllBookmarks() {
        BookmarkClient.fetchBookmarks { items in
            Logger().d(tag: TAG, message: "fetchAllBookmarks: \(items.count) items")
            self.bookmarks = items
        }
    }
    
    func addToBookmarks(entry: RWEntry) {
        BookmarkClient.addToBookmarks(entry: entry, completion: { items in
            Logger().d(tag: TAG, message: "addToBookmarks")
        })
    }
    
    func removeFromBookmarks(entry: RWEntry) {
        BookmarkClient.addToBookmarks(entry: entry, completion: { items in
            Logger().d(tag: TAG, message: "addToBookmarks")
        })
    }
}
