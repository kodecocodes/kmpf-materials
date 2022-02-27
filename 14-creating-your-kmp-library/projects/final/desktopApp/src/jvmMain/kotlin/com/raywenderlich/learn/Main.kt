package com.raywenderlich.learn

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.raywenderlich.learn.action.Action.openLink
import com.raywenderlich.learn.data.model.RWEntry
import com.raywenderlich.learn.ui.bookmark.BookmarkViewModel
import com.raywenderlich.learn.ui.home.FeedViewModel
import com.raywenderlich.learn.ui.main.MainScreen
import com.raywenderlich.learn.ui.theme.RWTheme
import moe.tlaster.precompose.PreComposeWindow
import moe.tlaster.precompose.ui.viewModel

private lateinit var bookmarkViewModel: BookmarkViewModel
private lateinit var feedViewModel: FeedViewModel

private const val TAG = "main"

fun main() {

    application {
        val windowState = rememberWindowState(width = 460.dp, height = 900.dp)

        PreComposeWindow(
            onCloseRequest = ::exitApplication,
            state = windowState,
            title = "learn"
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
                        onOpenEntry = { openLink(it) },
                        onShareAsLink = {}
                    )
                }
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