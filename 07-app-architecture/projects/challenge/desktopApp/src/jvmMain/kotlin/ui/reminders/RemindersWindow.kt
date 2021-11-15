package ui.reminders

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.rememberWindowState
import com.raywenderlich.organize.presentation.RemindersViewModel

@Composable
fun RemindersWindow(
  viewModel: RemindersViewModel = RemindersViewModel(),
  onCloseRequest: () -> Unit,
  onAboutButtonClick: () -> Unit
) {
  Window(
    title = viewModel.title,
    state = rememberWindowState(width = 400.dp, height = 550.dp),
    resizable = true,
    onCloseRequest = onCloseRequest,
  ) {
    RemindersView(viewModel = viewModel, onAboutButtonClick = onAboutButtonClick)
  }
}