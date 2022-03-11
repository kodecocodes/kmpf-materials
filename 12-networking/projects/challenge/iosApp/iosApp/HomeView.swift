/// Copyright (c) 2022 Razeware LLC
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

import AlertToast
import SwiftUI
import SharedKit
import SDWebImageSwiftUI

struct HomeView: View {
  let TAG = "HomeView"

  @State private var filter = PLATFORM.all.description()

  @State private var showToast = false

  @EnvironmentObject private var feedViewModel: RWEntryViewModel

  var body: some View {
    let content = feedViewModel.getContent()

    let rows = [GridItem(.fixed(150))]

    NavigationView {
      ZStack(alignment: .topLeading) {
        Color("rw-dark")
        ScrollView(.vertical) {
          VStack {
            ScrollView(.horizontal) {
              LazyHGrid(rows: rows, alignment: .top) {
                ForEach(content, id: \.self) { item in
                  Button(action: {
                    self.filter = item.platform.description()
                  }, label: {
                    AnimatedImage(url: URL(string: "\(item.image)"))
                      .resizable()
                      .scaledToFit()
                      .cornerRadius(8)
                  })
                }
              }

              Spacer()
            }

            let items = feedViewModel.items[filter] ?? []

            ForEach(items, id: \.id) { item in
              RWEntryRow(item: item, addToBookmarks: true)
                .environmentObject(feedViewModel)
            }
          }
        }
        .navigationBarTitle("learn", displayMode: .inline)
      }
      .onReceive(feedViewModel.$profile) { item in
        showToast = item?.preferredUsername != nil
      }
      .toast(isPresenting: $showToast) {
        AlertToast(type: .regular, title: "Hello \(String(feedViewModel.profile?.preferredUsername ?? ""))")
      }
    }
  }
}
