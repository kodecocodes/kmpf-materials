
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.raywenderlich.organize.presentation.Screen
import ui.about.AboutView
import ui.reminders.RemindersView

fun main() {
  return application {
    var screenState by remember { mutableStateOf(Screen.Reminders) }

    Window(
      title = "Organize",
      state = rememberWindowState(width = 400.dp, height = 550.dp),
      resizable = true,
      onCloseRequest = ::exitApplication,
    ) {
      RemindersView(
        onAboutIconClick = { screenState = Screen.AboutDevice }
      )
    }

    if (screenState == Screen.AboutDevice) {
      Window(
        title = "About Device",
        state = WindowState(width = 300.dp, height = 450.dp),
        resizable = true,
        onCloseRequest = {
          screenState = Screen.Reminders
        },
      ) {
        AboutView()
      }
    }
  }
}