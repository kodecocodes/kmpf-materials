package com.raywenderlich.learn

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.raywenderlich.learn.data.model.RWEntry
import com.raywenderlich.learn.platform.Logger
import com.raywenderlich.learn.ui.bookmark.BookmarkViewModel
import com.raywenderlich.learn.ui.home.FeedViewModel
import com.raywenderlich.learn.ui.main.MainScreen
import com.raywenderlich.learn.ui.ui.theme.RWTheme
import com.raywenderlich.learn.ui.utils.getString
import moe.tlaster.precompose.PreComposeWindow
import moe.tlaster.precompose.ui.viewModel
import java.awt.Desktop
import java.net.URI

private lateinit var bookmarkViewModel: BookmarkViewModel
private lateinit var feedViewModel: FeedViewModel

private const val TAG = "main"

fun main() {

    application {
        val windowState = rememberWindowState(width = 460.dp, height = 900.dp)

        PreComposeWindow(
                onCloseRequest = ::exitApplication,
                state = windowState,
                title = getString(MR.strings.app_name)
        ) {
            bookmarkViewModel = viewModel {
                BookmarkViewModel()
            }

            feedViewModel = viewModel {
                FeedViewModel()
            }

            feedViewModel.fetchAllFeeds()
            bookmarkViewModel.getBookmarks()

            val items = feedViewModel.items
            val bookmarks = bookmarkViewModel.items

            Surface(modifier = Modifier.fillMaxSize()) {

                RWTheme {
                    MainScreen(
                            feeds = items,
                            bookmarks = bookmarks,
                            onUpdateBookmark = { updateBookmark(it) },
                            onOpenEntry = { openEntry(it) },
                            onShareAsLink = {}
                    )
                }
            }
        }
    }
}

private fun updateBookmark(item: RWEntry) {
    Logger.d(TAG, "updateBookmark | bookmarked=${item.bookmarked}")
    if (item.bookmarked) {
        removedFromBookmarks(item)
    } else {
        addToBookmarks(item)
    }
}

private fun addToBookmarks(item: RWEntry) {
    bookmarkViewModel.addAsBookmark(item.copy(bookmarked = true))
}

private fun removedFromBookmarks(item: RWEntry) {
    bookmarkViewModel.removeFromBookmark(item.copy(bookmarked = false))
}

fun openEntry(url: String) {
    try {
        val desktop = Desktop.getDesktop()
        desktop.browse(URI.create(url))
    } catch(e: Exception) {
        Logger.e(TAG, "Unable to open url. Reason: ${e.stackTrace}")
    }
}