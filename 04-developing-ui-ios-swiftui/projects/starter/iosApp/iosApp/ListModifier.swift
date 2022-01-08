//
//  ListModifier.swift
//  iosApp
//
//  Created by Kevin Moore on 12/2/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI

struct ListModifier: ViewModifier {

    func body(content: Content) -> some View {
        if #available(iOS 15.0, *) {
            content
                .listRowInsets(.init())
                .listRowSeparator(.hidden)
        } else {
            content
        }
    }
}

extension View {
    func withListModifier() -> some View {
        modifier(ListModifier())
    }
}
