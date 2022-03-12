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

import SharedKit

public class FeedClient {
  private init() { }

  public typealias FeedHandler = (_ platform: String, _ items: [RWEntry]) -> Void
  public typealias FeedHandlerImage = (_ url: String) -> Void

  public typealias ProfileHandler = (_ profile: GravatarEntry) -> Void

  public static let shared = FeedClient()

  private let feedPresenter = ServiceLocator.init().getFeedPresenter

  private var handler: FeedHandler?
  private var handlerImage: FeedHandlerImage?
  private var handlerProfile: ProfileHandler?

  public func getContent() -> [RWContent] {
    return feedPresenter.content
  }

  public func fetchProfile(completion: @escaping ProfileHandler) {
    feedPresenter.fetchMyGravatar(cb: self)
    handlerProfile = completion
  }

  public func fetchFeeds(completion: @escaping FeedHandler) {
    feedPresenter.fetchAllFeeds(cb: self)
    handler = completion
  }

  @MainActor
  public func fetchLinkImage(_ link: String, completion: @escaping FeedHandlerImage) {
    Task {
      do {
        let result = try await feedPresenter.fetchLinkImage(link: link)
        completion(result)
      } catch {
        Logger().e(tag: TAG, message: "Unable to fetch article image link")
      }
    }
  }
}

extension FeedClient: FeedData {
  public func onNewDataAvailable(items: [RWEntry], platform: PLATFORM, exception: KotlinException?) {
    Logger().d(tag: TAG, message: "onNewDataAvailable: \(items)")
    self.handler?(platform.description(), items)
  }

  public func onMyGravatarData(item: GravatarEntry) {
    Logger().d(tag: TAG, message: "onMyGravatarData")
    self.handlerProfile?(item)
  }
}
