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

import SwiftUI
import SharedKit

struct ContentView: View {
  @State private var tabSelection = 1

  @State private var searchText = ""

  @StateObject var feedViewModel = RWEntryViewModel()

  init() {
    let itemAppearance = UITabBarItemAppearance()
    itemAppearance.normal.iconColor = UIColor(Color.white)

    let tabBarAppearance = UITabBarAppearance()
    tabBarAppearance.backgroundColor = UIColor(Color("rw-dark"))
    tabBarAppearance.stackedLayoutAppearance = itemAppearance
    tabBarAppearance.inlineLayoutAppearance = itemAppearance
    tabBarAppearance.compactInlineLayoutAppearance = itemAppearance

    UITabBar.appearance().standardAppearance = tabBarAppearance
		if #available(iOS 15.0, *) {
			UITabBar.appearance().scrollEdgeAppearance = tabBarAppearance
		}
  }

  var body: some View {
    TabView(selection: $tabSelection) {
      HomeView()
        .tabItem {
          Image("ic_home")
          Text("Home")
        }
        .tag(1)
        .environmentObject(feedViewModel)

      BookmarkView()
        .tabItem {
          Image("ic_bookmark")
          Text("Bookmark")
        }
        .tag(2)
        .environmentObject(feedViewModel)

      LatestView()
        .tabItem {
          Image("ic_latest")
          Text("Latest")
        }
        .tag(3)
        .environmentObject(feedViewModel)

      SearchView(text: $searchText)
        .tabItem {
          Image("ic_search")
          Text("Search")
        }
        .tag(4)
        .environmentObject(feedViewModel)
    }
    .accentColor(Color("rw-green"))
    .onAppear {
      let navBarAppearance = UINavigationBarAppearance()
      navBarAppearance.backgroundColor = UIColor(Color("rw-dark"))
      navBarAppearance.shadowImage = UIImage()
      navBarAppearance.shadowColor = .clear
      navBarAppearance.backgroundImage = UIImage()
      navBarAppearance.largeTitleTextAttributes = [.foregroundColor: UIColor.white]

      if let uiFont = UIFont(name: "Bitter-Bold", size: 18) {
        navBarAppearance.titleTextAttributes = [
          .font: uiFont,
          .foregroundColor: UIColor.white
        ]
      }

      UINavigationBar.appearance().barTintColor = UIColor(Color("rw-dark"))
      UINavigationBar.appearance().isTranslucent = false
      UINavigationBar.appearance().standardAppearance = navBarAppearance
      UINavigationBar.appearance().scrollEdgeAppearance = navBarAppearance
      UINavigationBar.appearance().compactAppearance = navBarAppearance
    }
  }
}
