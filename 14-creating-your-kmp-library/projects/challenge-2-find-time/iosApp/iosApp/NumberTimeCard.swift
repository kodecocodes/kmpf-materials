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

struct NumberTimeCard: View {
  var timezone: String
  var time: String
  var hours: String
  var date: String
  var body: some View {
    HStack {
      VStack(alignment: .leading) {
        Text(timezone)
          .font(.system(size: 16.0))
          .bold()
          .foregroundColor(Color.black)
        Spacer().frame(height:32.0)
        Text(hours)
          .lineLimit(1)
          .font(.system(size: 14.0))
          .foregroundColor(Color.black)
      }
      .padding(.leading, 8).padding(.bottom, 16)
      Spacer()
      VStack(alignment: .trailing) {
        Text(time)
          .font(.system(size: 16.0))
          .bold()
          .foregroundColor(Color.black)
        Spacer().frame(height:32.0)
        Text(date)
          .lineLimit(1)
          .font(.system(size: 10.0))
          .foregroundColor(Color.black)
      }
      .padding(.trailing, 8).padding(.bottom, 16)
    }
    .frame(maxWidth: .infinity, minHeight: 90, alignment: Alignment(horizontal: .leading, vertical: .bottom))
    .cornerRadius(10)
    .overlay(
      RoundedRectangle(cornerRadius: 8)
        .stroke(Color(.sRGB, red: 150/255, green: 150/255, blue: 150/255, opacity: 1.0), lineWidth: 1)
    )
    .padding(.leading, 16).padding(.bottom, 16).padding(.trailing, 16)
//    .padding([.vertical, .horizontal])
  }
}

struct NumberTimeCard_Previews: PreviewProvider {
  static var previews: some View {
    NumberTimeCard(timezone: "America/Los_Angeles", time: "2:38 pm",
                   hours: "6.0 hours from local", date: "Sunday, October 17")
  }
}
