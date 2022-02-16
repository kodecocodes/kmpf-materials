package com.raywenderlich.learn

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.raywenderlich.learn.components.Toast
import com.raywenderlich.learn.data.model.RWEntry
import com.raywenderlich.learn.platform.Logger
import com.raywenderlich.learn.ui.bookmark.BookmarkViewModel
import com.raywenderlich.learn.ui.home.FeedViewModel
import com.raywenderlich.learn.ui.main.MainScreen
import com.raywenderlich.learn.ui.theme.RWTheme
import moe.tlaster.precompose.PreComposeWindow
import moe.tlaster.precompose.ui.viewModel
import java.awt.Desktop
import java.net.URI

private lateinit var bookmarkViewModel: BookmarkViewModel
private lateinit var feedViewModel: FeedViewModel

private const val TAG = "main"

private val showToast: MutableState<Boolean> = mutableStateOf(false)

fun main() {

  application {
    val windowState = rememberWindowState(width = 460.dp, height = 900.dp)

    PreComposeWindow(
      onCloseRequest = ::exitApplication,
      state = windowState,
      title = "Unsplash"
    ) {
      bookmarkViewModel = viewModel {
        BookmarkViewModel()
      }

      feedViewModel = viewModel {
        FeedViewModel()
      }

      feedViewModel.fetchAllFeeds()
      feedViewModel.fetchMyGravatar()
      bookmarkViewModel.getBookmarks()

      val items = feedViewModel.items
      val profile = feedViewModel.profile

      val name = profile.value.preferredUsername
      if (!name.isNullOrEmpty()) {
        showToast.value = true
      }

      val bookmarks = bookmarkViewModel.items

      Surface(modifier = Modifier.fillMaxSize()) {

        RWTheme {
          MainScreen(
            feeds = items,
            bookmarks = bookmarks,
            onAddToBookmarks = { updateBookmark(it) },
            onOpenEntry = { openEntry(it) }
          )
        }

        Toast("Hello $name", showToast)
      }
    }
  }
}

private fun updateBookmark(item: RWEntry) {
  if (item.bookmarked) {
    removedFromBookmarks(item)
  } else {
    addToBookmarks(item)
  }
}

private fun addToBookmarks(item: RWEntry) {
  bookmarkViewModel.addAsBookmark(item)
  bookmarkViewModel.getBookmarks()
}

private fun removedFromBookmarks(item: RWEntry) {
  bookmarkViewModel.removeFromBookmark(item)
  bookmarkViewModel.getBookmarks()
}

fun openEntry(url: String) {
  try {
    val desktop = Desktop.getDesktop()
    desktop.browse(URI.create(url))
  } catch(e: Exception) {
    Logger.e(TAG, "Unable to open url. Reason: ${e.stackTrace}")
  }
}