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

struct SearchView: View {
  @Binding var text: String

  @State private var isEditing = false

  @State private var showDialog = false

  @State private var selectedEntry: KodecoEntry?

  @EnvironmentObject private var feedViewModel: KodecoEntryViewModel

  var filteredItems: [KodecoEntry] {
    if self.text.isEmpty {
      return feedViewModel.items[PLATFORM.all.description()] ?? []
    } else {
      let all = feedViewModel.items[PLATFORM.all.description()] ?? []
      return all.filter { $0.title.contains(text) || $0.summary.contains(text) }
    }
  }

  var body: some View {
    NavigationView {
      ZStack(alignment: .topLeading) {
        Color("black-night")
        ScrollView(.vertical) {
          VStack {
            HStack {
              TextField("Search...", text: $text)
                .padding(8)
                .padding(.horizontal, 25)
                .background(Color(.systemGray6))
                .cornerRadius(8)
                .overlay(
                  HStack {
                    Image(systemName: "magnifyingglass")
                      .foregroundColor(.gray)
                      .frame(minWidth: 0, maxWidth: .infinity, alignment: .leading)
                      .padding(.leading, 8)

                    if isEditing {
                      Button(action: {
                        self.text = ""
                      }, label: {
                        Image(systemName: "multiply.circle.fill")
                          .foregroundColor(.gray)
                          .padding(.trailing, 8)
                      })
                    }
                  }
                )
                .padding(.horizontal, 10)
                .onTapGesture {
                  self.isEditing = true
                }

              if isEditing {
                Button(action: {
                  self.isEditing = false
                  self.text = ""

                  UIApplication.shared.sendAction(
                    #selector(UIResponder.resignFirstResponder),
                    to: nil,
                    from: nil,
                    for: nil)
                }, label: {
                  Text("Cancel")
                })
                .padding(.trailing, 10)
                .transition(.move(edge: .trailing))
              }
            }
            .padding(.vertical, 8.0)

            ForEach(filteredItems, id: \.id) { item in
              KodecoEntryRow(item: item, addToBookmarks: true)
                .environmentObject(feedViewModel)
            }
          }
        }
      }
      .navigationBarTitle("learn", displayMode: .inline)
    }
  }
}
