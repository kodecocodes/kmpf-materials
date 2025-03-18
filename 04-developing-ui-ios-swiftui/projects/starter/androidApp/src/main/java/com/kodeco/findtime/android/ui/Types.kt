package com.kodeco.findtime.android.ui

import androidx.compose.runtime.Composable

// 1
typealias OnAddType =  (List<String>) -> Unit
// 2
typealias onDismissType =  () -> Unit
// 3
typealias composeFun =  @Composable () -> Unit
// 4
typealias topBarFun =  @Composable (Int) -> Unit

// 5
@Composable
fun EmptyComposable() {
}