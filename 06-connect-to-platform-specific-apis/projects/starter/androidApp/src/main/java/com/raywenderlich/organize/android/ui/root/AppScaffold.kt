package com.raywenderlich.organize.android.ui.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController

@Composable
fun AppScaffold() {
  val navController = rememberNavController()

  Scaffold {
    AppNavHost(
      navController = navController,
      modifier = Modifier
        .fillMaxSize()
        .padding(it)
    )
  }
}