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

struct BookmarkView: View {
    
    let TAG = "BookmarkView"
    
    @State private var showActionSheet = false
    
    @ObservedObject var bookmarkViewModel = RWEntryViewModel()
    
    var body: some View {
        
        NavigationView {
            ZStack(alignment: .topLeading) {
                Color("rw-dark")
                if(bookmarkViewModel.bookmarks.isEmpty) {
                    VStack(alignment: .center) {
                        Text("You currently don't have any bookmark.")
                            .foregroundColor(.white)
                            .font(Font.custom("Bitter-Bold", size: 15))
                    }
                    .frame(minWidth: 0, maxWidth: .infinity, minHeight: 0, maxHeight: .infinity, alignment: .center)
                    .background(Color("rw-dark"))
                    .navigationBarTitle("learn", displayMode: .inline)
                    
                } else {
                    ScrollView(.vertical) {
                        ForEach(bookmarkViewModel.bookmarks, id: \.id) { item in
                            VStack(alignment: .leading) {
                                HStack {
                                    AppIcon()
                                    VStack(alignment: .leading) {
                                        Text("Ray Wenderlich")
                                            .foregroundColor(.white)
                                            .font(Font.custom("Bitter-Bold", size: 15))
                                        Text(formatStringDate(date: item.updated))
                                            .foregroundColor(.white)
                                            .font(Font.custom("Bitter-Bold", size: 12))
                                    }
                                    
                                    Spacer()
                                    
                                    Button(action: {
                                        showActionSheet = true
                                    }) {
                                        Image("ic_more").foregroundColor(.white)
                                    }
                                    .padding(.trailing, 1)
                                    
                                    .actionSheet(isPresented: $showActionSheet) {
                                        ActionSheet(title: Text(""),
                                                    message: Text("Select an option"),
                                                    buttons: [
                                                        .cancel(),
                                                        .default(
                                                            Text("Remove from bookmarks"),
                                                            action: { bookmarkViewModel.removeFromBookmarks(entry: item) }
                                                        ),
                                                        .default(
                                                            Text("Share as link"),
                                                            action: {
                                                                guard let data = URL(string: item.link) else { return }
                                                                let av = UIActivityViewController(activityItems: [data], applicationActivities: nil)
                                                                UIApplication.shared.windows.first?.rootViewController?.present(av, animated: true, completion: nil)
                                                            }
                                                        )
                                                    ]
                                        )
                                    }
                                }
                                .frame(maxWidth: .infinity, alignment: .leading)
                                Text(item.title)
                                    .lineLimit(2)
                                    .foregroundColor(.white)
                                    .font(Font.custom("Bitter-Bold", size: 15))
                                Text(item.summary)
                                    .lineLimit(2)
                                    .foregroundColor(.white)
                                    .font(Font.custom("Bitter-Regular", size: 14))
                            }
                            .padding(.horizontal, 16.0)
                        }
                    }
                    .navigationBarTitle("learn", displayMode: .inline)
                }
            }
        }
        .onAppear() {
            Logger().d(tag: TAG, message: "Retrieving all bookmarks")
            bookmarkViewModel.fetchAllBookmarks()
        }
    }
}
