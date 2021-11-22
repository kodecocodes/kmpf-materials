//
//  HourSheet.swift
//  iosApp
//
//  Created by Kevin Moore on 11/20/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI

struct HourSheet: View {
  @Binding var hours: [Int]
  @Binding var showHoursDialog: Bool

  var body: some View {
    List {
      ForEach(hours, id: \.self) {  hour in
        Text("\(hour)")
      }
    }
    Spacer()
    Button("Dismiss") {
      showHoursDialog = false
    }

  }
}

struct HourSheet_Previews: PreviewProvider {
    static var previews: some View {
      HourSheet(hours: .constant([8, 9, 10]), showHoursDialog: .constant(true))
    }
}
