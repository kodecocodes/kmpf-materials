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
import SDWebImageSwiftUI

struct LatestView: View {
    
    @Environment(\.openURL) var openURL
    
    let TAG = "LatestView"
    
    @ObservedObject var feedViewModel = RWEntryViewModel()
        
    var body: some View {
        
        NavigationView {
            ZStack{
                Color("rw-dark")
                ScrollView(.vertical) {
                    VStack {
                        
                        ForEach(Array(feedViewModel.items.keys), id: \.self) { key in
                            Section(platform: key, entries:feedViewModel.items[key] ?? [])
                        }
                        
                    }
                    .navigationBarTitle("learn", displayMode: .inline)
                }
            }
        }
        .onAppear() {
            Logger().d(tag: TAG, message: "Retrieving all feeds")
            feedViewModel.fetchFeeds()
        }
    }
}

let ITEMS_SECTION = 4

struct Section: View {
    
    @Environment(\.openURL) var openURL
    
    @State var platform: String
    
    var entries: [RWEntry]
    
    var body: some View {
        
        LazyVStack {
            HStack {
                Text(platform)
                    .foregroundColor(.white)
                    .font(Font.custom("Bitter-Bold", size: 18))
                    .multilineTextAlignment(/*@START_MENU_TOKEN@*/.leading/*@END_MENU_TOKEN@*/)
                
                Spacer()
            }
            .padding(.leading, 10)
            .frame(maxWidth: .infinity)
            
            let subEntries = entries[0...ITEMS_SECTION]
            
            TabView {
                ForEach(subEntries, id: \.id) { item in
                    VStack{
                        Button(action: {
                            openURL(URL(string: "\(item.link)")!)
                        }, label: {
                            if (item.imageUrl == "") {
                              Rectangle().foregroundColor(.gray)
                              Image("razerware")
                            }
                            AnimatedImage(url: URL(string: "\(item.imageUrl)"))
                                .resizable()
                                .scaledToFit()
                                .cornerRadius(8)
                            
                        })
                    }
                }
            }
            .frame(width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.width)
            .tabViewStyle(PageTabViewStyle())
        }
    }
}
