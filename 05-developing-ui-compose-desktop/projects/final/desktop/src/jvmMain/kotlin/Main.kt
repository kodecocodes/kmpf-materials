import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyShortcut
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.kodeco.compose.ui.MainView
import com.kodeco.compose.ui.MyApplicationTheme

data class WindowInfo(val windowName: String, val windowState: WindowState)

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    application {
        var initialized by remember { mutableStateOf(false) }
        var windowCount by remember { mutableStateOf(1) }
        val windowList = remember { SnapshotStateList<WindowInfo>() }
// Add initial window
        if (!initialized) {
            windowList.add(WindowInfo("Timezone-${windowCount}", rememberWindowState()))
            initialized = true
        }
        windowList.forEachIndexed { i, window ->
            Window(
                onCloseRequest = {
                    windowList.removeAt(i)
                },
                state = windowList[i].windowState,
                title = windowList[i].windowName
            ) {
                MenuBar {
                    Menu("File", mnemonic = 'F') {
                        val nextWindowState = rememberWindowState()
                        Item(
                            "New", onClick = {
                                windowCount++
                                windowList.add(
                                    WindowInfo(
                                        "Timezone-${windowCount}",
                                        nextWindowState
                                    )
                                )
                            }, shortcut = KeyShortcut(
                                Key.N, ctrl = true
                            )
                        )
                        Item("Open", onClick = { }, shortcut = KeyShortcut(Key.O, ctrl = true))
                        Item("Close", onClick = {
                            windowList.removeAt(i)

                        }, shortcut = KeyShortcut(Key.W, ctrl = true))
                        Item("Save", onClick = { }, shortcut = KeyShortcut(Key.S, ctrl = true))
                        Separator()
                        Item(
                            "Exit",
                            onClick = { windowList.clear() },
                        )
                    }
                    Menu("Edit", mnemonic = 'E') {
                        Item(
                            "Cut", onClick = { }, shortcut = KeyShortcut(
                                Key.X, ctrl = true
                            )
                        )
                        Item(
                            "Copy", onClick = { }, shortcut = KeyShortcut(
                                Key.C, ctrl = true
                            )
                        )
                        Item("Paste", onClick = { }, shortcut = KeyShortcut(Key.V, ctrl = true))
                    }
                    Surface(modifier = Modifier.fillMaxSize()) {
                        MyApplicationTheme {
                            MainView()
                        }
                    }
                }
            }
        }
    }
}