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
import SDWebImageSwiftUI

struct BookmarkView: View {
  let TAG = "BookmarkView"

  @State private var showDialog = false

  @State private var selectedEntry: KodecoEntry?

  @EnvironmentObject private var feedViewModel: KodecoEntryViewModel

  var body: some View {
    NavigationView {
      ZStack(alignment: .topLeading) {
        Color("black-night")
        if feedViewModel.bookmarks.isEmpty {
          VStack(alignment: .center) {
            Text("You currently don't have any bookmark.")
              .foregroundColor(.white)
              .font(Font.custom("OpenSans-Bold", size: 15))
          }
          .frame(minWidth: 0, maxWidth: .infinity, minHeight: 0, maxHeight: .infinity, alignment: .center)
          .background(Color("black-night"))
        } else {
          ScrollView(.vertical) {
            ForEach(feedViewModel.bookmarks, id: \.id) { item in
              KodecoEntryRow(item: item, addToBookmarks: false)
                .environmentObject(feedViewModel)
            }
          }
        }
      }
      .navigationTitle("learn")
      .toolbar {
        MainToolbarContent()
      }
    }
    .onAppear {
      Logger().d(tag: TAG, message: "Retrieving all bookmarks")
      feedViewModel.fetchAllBookmarks()
    }
  }
}
