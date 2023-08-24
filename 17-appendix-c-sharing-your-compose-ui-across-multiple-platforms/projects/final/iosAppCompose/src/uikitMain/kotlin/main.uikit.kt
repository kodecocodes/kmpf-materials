@file:OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.kodeco.learn.data.model.KodecoEntry
import com.kodeco.learn.data.model.PLATFORM
import com.kodeco.learn.ui.bookmark.BookmarkViewModel
import com.kodeco.learn.ui.home.AddPlatformHeadings
import com.kodeco.learn.ui.home.FeedViewModel
import com.kodeco.learn.ui.home.HomeContent
import com.kodeco.learn.ui.main.MainScreen
import com.kodeco.learn.ui.theme.Fonts
import com.kodeco.learn.ui.theme.KodecoTheme
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCObjectBase
import kotlinx.cinterop.autoreleasepool
import kotlinx.cinterop.cstr
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.toCValues
import moe.tlaster.precompose.PreComposeApplication
import moe.tlaster.precompose.viewmodel.viewModel
import platform.Foundation.NSStringFromClass
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationDelegateProtocol
import platform.UIKit.UIApplicationDelegateProtocolMeta
import platform.UIKit.UIApplicationMain
import platform.UIKit.UIResponder
import platform.UIKit.UIResponderMeta
import platform.UIKit.UIScreen
import platform.UIKit.UIWindow

@OptIn(ExperimentalForeignApi::class)
fun main() {
    val args = emptyArray<String>()
    memScoped {
        val argc = args.size + 1
        val argv = (arrayOf("skikoApp") + args).map { it.cstr.ptr }.toCValues()
        autoreleasepool {
            UIApplicationMain(argc, argv, null, NSStringFromClass(SkikoAppDelegate))
        }
    }
}

private lateinit var bookmarkViewModel: BookmarkViewModel
private lateinit var feedViewModel: FeedViewModel

class SkikoAppDelegate : UIResponder, UIApplicationDelegateProtocol {
    companion object : UIResponderMeta(), UIApplicationDelegateProtocolMeta

    @ObjCObjectBase.OverrideInit
    constructor() : super()

    private var _window: UIWindow? = null
    override fun window() = _window
    override fun setWindow(window: UIWindow?) {
        _window = window
    }

    override fun application(application: UIApplication, didFinishLaunchingWithOptions: Map<Any?, *>?): Boolean {
        window = UIWindow(frame = UIScreen.mainScreen.bounds)
        window!!.rootViewController = PreComposeApplication {
            Surface(modifier = Modifier.fillMaxSize()) {
                CompositionLocalProvider {
                    bookmarkViewModel = viewModel(
                        modelClass = BookmarkViewModel::class,
                        creator = { BookmarkViewModel() }
                    )

                    feedViewModel = viewModel(
                        modelClass = FeedViewModel::class,
                        creator = { FeedViewModel() }
                    )

                    feedViewModel.fetchAllFeeds()
                    feedViewModel.fetchMyGravatar()
                    bookmarkViewModel.getBookmarks()

                    val items = feedViewModel.items
                    val profile = feedViewModel.profile
                    val bookmarks = bookmarkViewModel.items

                    KodecoTheme {
                        /*MainScreen(
                            avatarUrl = profile.value.thumbnailUrl,
                            feeds = items,
                            bookmarks = bookmarks,
                            onUpdateBookmark = { updateBookmark(it) },
                            onShareAsLink = {},
                            onOpenEntry = {
                                //Do nothing
                            }
                        )*/
                        val platform = remember { mutableStateOf(PLATFORM.ANDROID) }

                        /*AddPlatformHeadings(
                                platform
                        )*/

                        androidx.compose.material3.Text(
                                text = platform.value.value,
                                fontFamily = Fonts.OpenSansFontFamily()
                        )
                    }
                }
            }
        }
        window!!.makeKeyAndVisible()
        return true
    }
}

private fun updateBookmark(item: KodecoEntry) {
    if (item.bookmarked) {
        removedFromBookmarks(item)
    } else {
        addToBookmarks(item)
    }
}

private fun addToBookmarks(item: KodecoEntry) {
    bookmarkViewModel.addAsBookmark(item)
    bookmarkViewModel.getBookmarks()
}

private fun removedFromBookmarks(item: KodecoEntry) {
    bookmarkViewModel.removeFromBookmark(item)
    bookmarkViewModel.getBookmarks()
}